package ubb.models.statements;

import ubb.business.view_controllers.Pair;
import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyDictionary;
import ubb.models.adts.MyIDictionary;
import ubb.models.expressions.IExpression;
import ubb.models.types.Type;
import ubb.models.values.IValue;

import java.util.List;

public class CallFunctionStatement implements IStatement {
    //USAGE: call functionName(parameter1, parameter2, ...) =>
    // new CallFunctionStatement("functionName", List.of(parameter1, parameter2, ...))
    /***
     * functionName is a string
     * parameter1, parameter2, ... are expressions
     */

    private final String functionName;
    private final List<IExpression> functionParameters;

    public CallFunctionStatement(String functionName, List<IExpression> functionParameters) {
        this.functionName = functionName;
        this.functionParameters = functionParameters;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        if (!currentState.getProcedureTable().isDefined(this.functionName))
            throw new InterpreterException("Thread " + currentState.getId() + ": Procedure " + this.functionName + " is not defined!");

        MyIDictionary<String, IValue> newSymbolTable = new MyDictionary<>();

        Pair<List<String>, IStatement> procedure = currentState.getProcedureTable().get(this.functionName);
        List<String> procedureParameters = procedure.getFirst();

        if (procedureParameters.size() != this.functionParameters.size())
            throw new InterpreterException("Thread " + currentState.getId() + ": Procedure " + this.functionName + " has "
                + procedure.getFirst().size() + " parameters, but " + this.functionParameters.size() + " were given!");

        for (int i = 0; i < this.functionParameters.size(); i++)
            newSymbolTable.put(procedureParameters.get(i),
                this.functionParameters.get(i).evaluate(
                    currentState.getSymbolTable(),
                    currentState.getHeapTable(),
                    currentState.getId()
                )
            );

        currentState.addSymbolTable(newSymbolTable);
        currentState.getStack().push(new ReturnStatement());
        currentState.getStack().push(procedure.getSecond());

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "call " + this.functionName + "(" + this.functionParameters + ")";
    }
}
