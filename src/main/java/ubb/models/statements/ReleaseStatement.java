package ubb.models.statements;

import ubb.business.Pair;
import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.IntType;
import ubb.models.types.Type;
import ubb.models.values.IntValue;

import java.util.List;

public class ReleaseStatement implements IStatement {
    private final String variableId;

    public ReleaseStatement(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        if (!currentState.getSymbolTable().isDefined(variableId))
            throw new InterpreterException(String.format("Thread %d: Semaphore variable is not defined.", currentState.getId()));

        if (!currentState.getSymbolTable().get(variableId).getType().equals(new IntType()))
            throw new InterpreterException(String.format("Thread %d: Semaphore variable is not an integer.", currentState.getId()));

        Integer foundIndex = ((IntValue) currentState.getSymbolTable().get(variableId)).getValue();

        if (!currentState.getSemaphoreTable().isDefined(foundIndex))
            throw new InterpreterException(String.format("Thread %d: Semaphore variable is not defined.", currentState.getId()));

        Pair<Integer, List<Integer>> foundSemaphore = currentState.getSemaphoreTable().getSemaphoreWithKey(foundIndex);

        Integer idToRemove = currentState.getId();

        if (foundSemaphore.second.contains(currentState.getId()))
            foundSemaphore.second.remove(idToRemove);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        if (!typeTable.isDefined(variableId))
            throw new InterpreterException(String.format(
                "Release statement: variable %s is not defined.",
                variableId)
            );

        if (!typeTable.get(variableId).equals(new IntType()))
            throw new InterpreterException(String.format(
                "Release statement: variable %s is not an integer.",
                variableId)
            );

        return typeTable;
    }

    @Override
    public String toString() {
        return "release(" + this.variableId + ")";
    }
}
