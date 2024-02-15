package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.expressions.IExpression;
import ubb.models.expressions.RelationalExpression;
import ubb.models.expressions.VariableExpression;
import ubb.models.types.IntType;
import ubb.models.types.Type;

public class ForStatement implements IStatement {
    private final IExpression firstExpression, secondExpression, thirdExpression;
    private final String variableId;
    private final IStatement innerStatement;

    public ForStatement(String variableName, IExpression firstExpression, IExpression secondExpression,
                        IExpression thirdExpression, IStatement innerStatement)
    {
        this.variableId = variableName;
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.thirdExpression = thirdExpression;
        this.innerStatement = innerStatement;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        IStatement convertedStatement = new CompoundStatement(
                new AssignStatement(this.variableId, this.firstExpression),
                new CompoundStatement(
                        new WhileStatement(
                                new RelationalExpression(
                                        new VariableExpression(variableId),
                                        this.secondExpression,
                                        "<"),
                                new CompoundStatement(
                                        innerStatement,
                                        new AssignStatement(variableId, thirdExpression)
                                )
                        ),
                        new NOPStatement()
                )
        );

        currentState.getStack().push(convertedStatement);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        if (!typeTable.isDefined(variableId))
            throw new InterpreterException(String.format("Variable %s is not defined!", variableId));

        if(!typeTable.get(variableId).equals(new IntType()))
            throw new InterpreterException(String.format("Variable %s is not of type int!", variableId));

        Type firstExpressionType = firstExpression.typeCheck(typeTable);
        Type secondExpressionType = secondExpression.typeCheck(typeTable);
        Type thirdExpressionType = thirdExpression.typeCheck(typeTable);

        if (!firstExpressionType.equals(new IntType()))
            throw new InterpreterException(String.format("Expression %s is not of type int!", firstExpression));

        if (!secondExpressionType.equals(new IntType()))
            throw new InterpreterException(String.format("Expression %s is not of type int!", secondExpression));

        if (!thirdExpressionType.equals(new IntType()))
            throw new InterpreterException(String.format("Expression %s is not of type int!", thirdExpression));

        innerStatement.typeCheck(typeTable.copy());

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("for(%s = %s; %s < %s; %s = %s) { %s}",
                variableId,
                firstExpression,
                variableId,
                secondExpression,
                variableId,
                thirdExpression,
                innerStatement);
    }
}
