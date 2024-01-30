package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.adts.MyIHeap;
import ubb.models.expressions.IExpression;
import ubb.models.types.IntType;
import ubb.models.types.Type;
import ubb.models.values.IValue;
import ubb.models.values.IntValue;

public class NewLatchStatement implements IStatement {
    private final String variableId;
    private final IExpression countExpression;

    public NewLatchStatement(String variableId, IExpression countExpression) {
        this.variableId = variableId;
        this.countExpression = countExpression;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        MyIDictionary<String, IValue> symbolTable = currentState.getSymbolTable();
        MyIHeap heapTable = currentState.getHeapTable();

        IValue expressionValue = countExpression.evaluate(symbolTable, heapTable, currentState.getId());
        Integer numberOfThreads = ((IntValue) expressionValue).getValue();

        Integer freeAddress = currentState.getLatchTable().getFreeAddress();
        currentState.getLatchTable().put(freeAddress, numberOfThreads);

        symbolTable.put(variableId, new IntValue(freeAddress));

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        Type expressionType = countExpression.typeCheck(typeTable);

        if (!expressionType.equals(new IntType()))
            throw new InterpreterException("NewLatchStatement: expression is not of type int");

        if (!typeTable.isDefined(variableId))
            throw new InterpreterException("NewLatchStatement: variable is not defined");

        Type variableType = typeTable.get(variableId);

        if (!variableType.equals(new IntType()))
            throw new InterpreterException("NewLatchStatement: variable is not of type int");

        return typeTable;
    }

    @Override
    public String toString() {
        return "newLatch(" + this.variableId + ", " + this.countExpression.toString() + ")";
    }
}
