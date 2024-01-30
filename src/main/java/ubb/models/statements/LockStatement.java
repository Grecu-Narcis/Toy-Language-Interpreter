package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.IntType;
import ubb.models.types.Type;
import ubb.models.values.IValue;
import ubb.models.values.IntValue;

public class LockStatement implements IStatement {
    private final String variableId;

    public LockStatement(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        MyIDictionary<String, IValue> symbolTable = currentState.getSymbolTable();

        if (!symbolTable.isDefined(variableId))
            throw new InterpreterException(String.format("Thread %d: Variable %s is not defined",
                currentState.getId(), variableId));

        Integer foundIndex = ((IntValue) symbolTable.get(variableId)).getValue();

        if (!currentState.getLockTable().isDefined(foundIndex))
            throw new InterpreterException(String.format("Thread %d: Index %d is not defined in lock table!",
                currentState.getId(), foundIndex));

        if (currentState.getLockTable().getValueAtAddress(foundIndex) == -1)
            currentState.getLockTable().update(foundIndex, currentState.getId());

        else
            currentState.getStack().push(this);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        if (!typeTable.isDefined(variableId))
            throw new InterpreterException(String.format("Lock Statement: Variable %s is not defined", variableId));

        if (!typeTable.get(variableId).equals(new IntType()))
            throw new InterpreterException(String.format("Lock Statement: Variable %s is not an integer", variableId));

        return typeTable;
    }

    @Override
    public String toString() {
        return "lock(" + variableId + ")";
    }
}
