package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.expressions.IExpression;
import ubb.models.expressions.RelationalExpression;
import ubb.models.types.Type;

public class SwitchStatement implements IStatement {
    private final IExpression switchExpression, firstCaseExpression, secondCaseExpression;
    private final IStatement firstCaseStatement, secondCaseStatement, defaultStatement;

    public SwitchStatement(IExpression switchExpression, IExpression firstCaseExpression,
                           IExpression secondCaseExpression, IStatement firstCaseStatement,
                           IStatement secondCaseStatement, IStatement defaultStatement) {
        this.switchExpression = switchExpression;
        this.firstCaseExpression = firstCaseExpression;
        this.secondCaseExpression = secondCaseExpression;
        this.firstCaseStatement = firstCaseStatement;
        this.secondCaseStatement = secondCaseStatement;
        this.defaultStatement = defaultStatement;
    }

    /***
     * Converts a switch statement into an if statement.
     * @param currentState The current program state.
     * @return null
     */
    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        IStatement convertedStatement =
                new IfStatement(
                        new RelationalExpression(switchExpression, firstCaseExpression, "=="),
                        firstCaseStatement,
                        new IfStatement(
                                new RelationalExpression(switchExpression, secondCaseExpression, "=="),
                                secondCaseStatement,
                                defaultStatement
                        )
                );

        currentState.getStack().push(convertedStatement);

        return null;
    }

    /***
     * Checks if the switch expression and the case expressions have the same type.
     * @param typeTable The type table containing variable types.
     * @return The type table.
     * @throws InterpreterException If the switch expression and the case expressions do not have the same type.
     */
    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        Type switchExpressionType = switchExpression.typeCheck(typeTable);
        Type firstCaseExpressionType = firstCaseExpression.typeCheck(typeTable);
        Type secondCaseExpressionType = secondCaseExpression.typeCheck(typeTable);

        if (!switchExpressionType.equals(firstCaseExpressionType))
            throw new InterpreterException("Switch statement: switch expression and first case expression do not have the same type!");

        if (!switchExpressionType.equals(secondCaseExpressionType))
            throw new InterpreterException("Switch statement: switch expression and second case expression do not have the same type!");

        firstCaseStatement.typeCheck(typeTable);
        secondCaseStatement.typeCheck(typeTable);
        defaultStatement.typeCheck(typeTable);

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("switch (%s) { case %s: %s case %s: {%s} default: {%s} }",
                switchExpression.toString(),
                firstCaseExpression.toString(),
                firstCaseStatement.toString(),
                secondCaseExpression.toString(),
                secondCaseStatement.toString(),
                defaultStatement.toString());
    }
}
