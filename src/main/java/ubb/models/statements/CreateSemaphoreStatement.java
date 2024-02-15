package ubb.models.statements;

import ubb.business.Pair;
import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.expressions.IExpression;
import ubb.models.types.IntType;
import ubb.models.types.Type;
import ubb.models.values.IValue;
import ubb.models.values.IntValue;

import java.util.ArrayList;

public class CreateSemaphoreStatement implements IStatement {
    private final String variableId;
    private final IExpression threadsNumberExpression;

    public CreateSemaphoreStatement(String variableId, IExpression threadsNumberExpression)
    {
        this.variableId = variableId;
        this.threadsNumberExpression = threadsNumberExpression;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        IValue threadsNumberValue = threadsNumberExpression.evaluate(
            currentState.getSymbolTable(),
            currentState.getHeapTable(),
            currentState.getId()
        );

        if (!threadsNumberValue.getType().equals(new IntType()))
            throw new InterpreterException(String.format("Thread %d: Semaphore threads number expression is not an integer.", currentState.getId()));

        if (!currentState.getSymbolTable().isDefined(variableId))
            throw new InterpreterException(String.format("Thread %d: Semaphore variable is not defined.", currentState.getId()));

        if (!currentState.getSymbolTable().get(variableId).getType().equals(new IntType()))
            throw new InterpreterException(String.format("Thread %d: Semaphore variable is not an integer.", currentState.getId()));

        Integer freeLocation = currentState.getSemaphoreTable().getFreeAddress();
        Integer threadsNumber = ((IntValue) threadsNumberValue).getValue();

        currentState.getSemaphoreTable().add(freeLocation, new Pair<>(threadsNumber, new ArrayList<>()));
        currentState.getSymbolTable().put(variableId, new IntValue(freeLocation));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        if (!typeTable.isDefined(variableId))
            throw new InterpreterException(String.format(
                "Create semaphore statement: variable %s is not defined.",
                variableId)
            );

        if (!typeTable.get(variableId).equals(new IntType()))
            throw new InterpreterException(String.format(
                "Create semaphore statement: variable %s is not an integer.",
                variableId)
            );

        if (!threadsNumberExpression.typeCheck(typeTable).equals(new IntType()))
            throw new InterpreterException(String.format(
                "Create semaphore statement: threads number expression %s is not an integer.",
                threadsNumberExpression)
            );

        return typeTable;
    }

    @Override
    public String toString() {
        return "createSemaphore(" + this.variableId + ", " + this.threadsNumberExpression.toString() + ")";
    }
}
