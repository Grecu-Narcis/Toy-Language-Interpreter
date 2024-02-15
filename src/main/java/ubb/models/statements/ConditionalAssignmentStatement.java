package ubb.models.statements;

import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.expressions.IExpression;
import ubb.models.types.BoolType;
import ubb.models.types.Type;

public class ConditionalAssignmentStatement implements IStatement {
    private final String variableID;
    private final IExpression condition, valueIfTrue, valueIfFalse;

    public ConditionalAssignmentStatement(String variableID, IExpression condition,
                                          IExpression valueIfTrue, IExpression valueIfFalse) {
        this.variableID = variableID;
        this.condition = condition;
        this.valueIfTrue = valueIfTrue;
        this.valueIfFalse = valueIfFalse;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        IStatement convertedStatement = new IfStatement(
            condition,
            new AssignStatement(
                "v",
                valueIfTrue
            ),
            new AssignStatement(
                "v",
                valueIfFalse
            )
        );

        currentState.getStack().push(convertedStatement);

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        // Check if the condition is of type bool
        Type conditionType = condition.typeCheck(typeTable);

        if (!conditionType.equals(new BoolType()))
            throw new InterpreterException(String.format("Conditional assignment: condition %s is not of type bool!", condition));

        // Check if the variable is defined
        if (!typeTable.isDefined(variableID))
            throw new InterpreterException(String.format("Conditional assignment: variable %s is not defined!", variableID));

        // Check if the types of the variable and the expressions match
        Type variableType = typeTable.get(variableID);
        Type valueIfTrueType = valueIfTrue.typeCheck(typeTable);
        Type valueIfFalseType = valueIfFalse.typeCheck(typeTable);

        if (!variableType.equals(valueIfTrueType))
            throw new InterpreterException(String.format("Conditional assignment: types of variable %s and expression %s do not match!",
                variableID, valueIfTrue));

        if (!variableType.equals(valueIfFalseType))
            throw new InterpreterException(String.format("Conditional assignment: types of variable %s and expression %s do not match!",
                variableID, valueIfFalse));

        return typeTable;
    }

    @Override
    public String toString() {
        return String.format("%s = %s ? %s : %s", variableID, condition, valueIfTrue, valueIfFalse);
    }
}
