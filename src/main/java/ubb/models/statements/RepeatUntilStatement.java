package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.expressions.IExpression;
import ubb.models.expressions.NotExpression;
import ubb.models.types.BoolType;
import ubb.models.types.Type;

public class RepeatUntilStatement implements IStatement {
    private final IStatement innerStatement;
    private final IExpression conditionExpression;

    public RepeatUntilStatement(IStatement innerStatement, IExpression conditionExpression) {
        this.innerStatement = innerStatement;
        this.conditionExpression = conditionExpression;
    }

    /***
     * Executes a RepeatUntilStatement, converting it to a WhileStatement and pushing it onto the program stack.
     * @param currentState The current program state.
     * @return null
     */
    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        IStatement convertedStatement =
                new CompoundStatement(
                        innerStatement,
                        new WhileStatement(
                                new NotExpression(conditionExpression),
                                innerStatement
                        )
                );

        currentState.getStack().push(convertedStatement);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        Type conditionExpressionType = conditionExpression.typeCheck(typeTable);
        if (!conditionExpressionType.equals(new BoolType()))
            throw new InterpreterException("Repeat until statement condition expression is not boolean!");

        innerStatement.typeCheck(typeTable);

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("repeat {%s} until (%s)", innerStatement.toString(), conditionExpression.toString());
    }
}
