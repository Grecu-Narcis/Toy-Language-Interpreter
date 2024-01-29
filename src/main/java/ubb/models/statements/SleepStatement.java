package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.Type;

public class SleepStatement implements IStatement {
    private final int number;

    public SleepStatement(int number)
    {
        this.number = number;
    }

    /**
     * Decrements the number of seconds to sleep and pushes a new sleep statement on the stack
     * @param currentState the current program state
     * @return null
     */

    @Override
    public ProgramState execute(ProgramState currentState) {
        if (number > 0)
            currentState.getStack().push(new SleepStatement(number - 1));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "sleep(" + number + ")";
    }
}
