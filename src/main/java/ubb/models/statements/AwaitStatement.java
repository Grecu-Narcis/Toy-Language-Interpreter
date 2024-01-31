package ubb.models.statements;

import ubb.business.Pair;
import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.IntType;
import ubb.models.types.Type;
import ubb.models.values.IntValue;

import java.util.List;

public class AwaitStatement implements IStatement {
    private final String variableId;

    public AwaitStatement(String variableId) {
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        if (!currentState.getSymbolTable().isDefined(variableId))
            throw new InterpreterException(String.format(
                "Thread %d: AwaitStatement: Variable is not defined.",
                currentState.getId()
                )
            );

        Integer foundIndex = ((IntValue) currentState.getSymbolTable().get(variableId)).getValue();

        if (!currentState.getBarrierTable().isDefined(foundIndex))
            throw new InterpreterException(String.format(
                "Thread %d: AwaitStatement: Barrier is not defined.",
                currentState.getId()
                )
            );

        Pair<Integer, List<Integer>> barrierFound = currentState.getBarrierTable().getBarrier(foundIndex);

        if (barrierFound.first > barrierFound.second.size())
        {
            currentState.getStack().push(this);

            if (!barrierFound.second.contains(currentState.getId()))
                barrierFound.second.add(currentState.getId());
        }

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
//        if (!typeTable.isDefined(variableId))
//            throw new InterpreterException("AwaitStatement: Variable is not defined.");
//
//        if (!typeTable.get(variableId).equals(new IntType()))
//            throw new InterpreterException("AwaitStatement: Variable is not of type int.");

        return typeTable;
    }

    @Override
    public String toString() {
        return "await(" + this.variableId + ")";
    }
}
