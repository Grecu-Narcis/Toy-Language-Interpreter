package ubb.models.expressions;

import ubb.exceptions.InterpreterException;
import ubb.models.adts.MyIDictionary;
import ubb.models.adts.MyIHeap;
import ubb.models.types.BoolType;
import ubb.models.types.Type;
import ubb.models.values.BoolValue;
import ubb.models.values.IValue;

public class NotExpression implements IExpression {
    private final IExpression expression;

    public NotExpression(IExpression expression) {
        this.expression = expression;
    }

    /***
     * Evaluates a boolean expression involving a single boolean operand and a logical operator.
     * @param symbolTable The symbol table containing variable bindings.
     * @param heapTable The heap table containing memory allocations.
     * @return a BoolValue representing the result of the boolean expression.
     * @throws InterpreterException If the operand is not a boolean or if an invalid logical operator is used.
     */
    @Override
    public IValue evaluate(MyIDictionary<String, IValue> symbolTable, MyIHeap heapTable, int threadID) throws InterpreterException {
        IValue expressionValue = expression.evaluate(symbolTable, heapTable, threadID);
        if (!expressionValue.getType().equals(new BoolType()))
            throw new InterpreterException("Expression used in not expression cannot be evaluated as boolean!");

        BoolValue boolValue = (BoolValue) expressionValue;
        return new BoolValue(!boolValue.getValue());
    }

    /***
     * Returns the type of the expression.
     * @param typeTable The type table containing variable type info.
     * @return The type of the expression.
     * @throws InterpreterException If the expression cannot be evaluated as a boolean.
     */
    @Override
    public Type typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        Type expressionType = expression.typeCheck(typeTable);
        if (!expressionType.equals(new BoolType()))
            throw new InterpreterException("Expression used in not expression cannot be evaluated as boolean!");

        return new BoolType();
    }

    @Override
    public String toString() {
        return String.format("!(%s)", expression);
    }
}
