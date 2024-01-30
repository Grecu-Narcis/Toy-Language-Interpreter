package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.Type;
import ubb.models.values.IValue;
import ubb.models.values.IntValue;
import ubb.models.values.StringValue;

public class CountDownStatement implements IStatement {
    private final String variableId;

    public CountDownStatement(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        if (!currentState.getSymbolTable().isDefined(variableId))
            throw new InterpreterException(String.format("Thread %d: CountDownStatement: variable %s is not defined",
                currentState.getId(), variableId));

        IValue variableValue = currentState.getSymbolTable().get(variableId);
        Integer foundIndex = ((IntValue) variableValue).getValue();

        if (!currentState.getLatchTable().isDefined(foundIndex))
            return null;

        Integer counterValue = currentState.getLatchTable().getValueAtAddress(foundIndex);

        if (counterValue > 0)
        {
            currentState.getLatchTable().update(foundIndex, counterValue - 1);
            currentState.getOutputList().add(new StringValue("Thread: " + currentState.getId()));
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "countDown(" + this.variableId + ")";
    }
}
