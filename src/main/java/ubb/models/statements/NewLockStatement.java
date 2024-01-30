package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.Type;
import ubb.models.values.IntValue;

public class NewLockStatement implements IStatement {
    private final String variableId;

    public NewLockStatement(String variableName) {
        this.variableId = variableName;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        int location = currentState.getLockTable().getFreeLocation();
        currentState.getLockTable().put(location, -1);

        if (currentState.getSymbolTable().isDefined(variableId)) {
            currentState.getSymbolTable().update(variableId, new IntValue(location));
        } else {
            currentState.getSymbolTable().put(variableId, new IntValue(location));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "newLock(" + variableId + ")";
    }
}
