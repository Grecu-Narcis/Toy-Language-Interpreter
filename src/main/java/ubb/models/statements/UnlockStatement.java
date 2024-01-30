package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.IntType;
import ubb.models.types.Type;
import ubb.models.values.IValue;
import ubb.models.values.IntValue;

public class UnlockStatement implements IStatement {
    private final String variableId;

    public UnlockStatement(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        MyIDictionary<String, IValue> symbolTable = currentState.getSymbolTable();

        if (!symbolTable.isDefined(variableId))
            throw new InterpreterException(String.format("Thread %d: Variable %s is not defined",
                currentState.getId(), variableId));

        Integer foundIndex = ((IntValue) symbolTable.get(variableId)).getValue();

        if (currentState.getLockTable().isDefined(foundIndex) &&
            currentState.getLockTable().getValueAtAddress(foundIndex) == currentState.getId())
            currentState.getLockTable().update(foundIndex, -1);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        if (!typeTable.isDefined(variableId))
            throw new InterpreterException(String.format("Unlock Statement: Variable %s is not defined", variableId));

        if (!typeTable.get(variableId).equals(new IntType()))
            throw new InterpreterException(String.format("Unlock Statement: Variable %s is not an integer", variableId));

        return typeTable;
    }

    @Override
    public String toString() {
        return "unlock(" + variableId + ")";
    }
}
