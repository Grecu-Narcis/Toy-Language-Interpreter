package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.IntType;
import ubb.models.types.Type;
import ubb.models.values.IValue;
import ubb.models.values.IntValue;

public class AwaitStatement implements IStatement {
    private final String variableId;

    public AwaitStatement(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        if (!currentState.getSymbolTable().isDefined(variableId))
            throw new InterpreterException(String.format("Thread %d: AwaitStatement: variable %s is not defined",
                currentState.getId(), variableId));

        IValue variableValue = currentState.getSymbolTable().get(variableId);
        Integer foundIndex = ((IntValue) variableValue).getValue();

        if (!currentState.getLatchTable().isDefined(foundIndex))
            throw new InterpreterException(String.format("Thread %d: AwaitStatement: index %d is not defined",
                currentState.getId(), foundIndex));

        if (currentState.getLatchTable().getValueAtAddress(foundIndex) > 0)
            currentState.getStack().push(this);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        if (!typeTable.isDefined(variableId))
            throw new InterpreterException("AwaitStatement: variable is not defined!");

        if (!typeTable.get(variableId).equals(new IntType()))
            throw new InterpreterException("AwaitStatement: variable is not of type int!");

        return typeTable;
    }

    @Override
    public String toString() {
        return "await(" + this.variableId + ")";
    }
}
