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

public class NewBarrierStatement implements IStatement {
    private final String variableId;
    private final IExpression countExpression;

    public NewBarrierStatement(String variableId, IExpression countExpression) {
        this.variableId = variableId;
        this.countExpression = countExpression;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        IValue countValue = countExpression.evaluate(currentState.getSymbolTable(),
            currentState.getHeapTable(), currentState.getId());

        Integer threadsCount = ((IntValue) countValue).getValue();

        Integer freeAddress = currentState.getBarrierTable().getFreeAddress();

        currentState.getBarrierTable().add(
            freeAddress,
            new Pair<>(threadsCount, new ArrayList<Integer>())
        );

        currentState.getSymbolTable().put(variableId, new IntValue(freeAddress));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        Type expressionType = countExpression.typeCheck(typeTable);

        if (!expressionType.equals(new IntType()))
            throw new InterpreterException("NewBarrierStatement: Expression is not of type int.");

        return typeTable;
    }

    @Override
    public String toString() {
        return "newBarrier(" + this.variableId + ", " + this.countExpression.toString() + ")";
    }
}
