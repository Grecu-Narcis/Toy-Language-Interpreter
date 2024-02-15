package ubb.presentation;

import ubb.models.expressions.*;
import ubb.models.statements.*;
import ubb.models.types.*;
import ubb.models.values.BoolValue;
import ubb.models.values.IntValue;
import ubb.models.values.StringValue;

import java.util.ArrayList;
import java.util.List;

public class Examples {
    public static IStatement createExample1() {
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new BoolType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new IntType()),
                                new NOPStatement()))
        );
    }

    public static IStatement createExample2()
    {
        return new CompoundStatement
                (
                        new VariableDeclarationStatement("a", new IntType()),
                        new CompoundStatement
                                (
                                        new VariableDeclarationStatement("b", new IntType()),
                                        new CompoundStatement
                                                (
                                                        new AssignStatement("a",
                                                                new ArithmeticExpression('+',
                                                                        new ValueExpression(new IntValue(2)),
                                                                        new ArithmeticExpression('*',
                                                                                new ValueExpression(new IntValue(3)),
                                                                                new ValueExpression(new IntValue(5)))
                                                                )
                                                        ),
                                                        new CompoundStatement(
                                                                new AssignStatement("b",
                                                                        new ArithmeticExpression('+',
                                                                                new VariableExpression("a"),
                                                                                new ValueExpression(new IntValue(1))
                                                                        )
                                                                ),
                                                                new PrintStatement(new VariableExpression("b"))
                                                        )
                                                )
                                )
                );
    }

    public static IStatement createExample3()
    {
        return new CompoundStatement(
                        new VariableDeclarationStatement("a", new BoolType()),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v", new IntType()),
                                new CompoundStatement(
                                        new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                        new CompoundStatement(
                                                new IfStatement(
                                                        new VariableExpression("a"),
                                                        new AssignStatement("v", new ValueExpression(new IntValue(2))),
                                                        new AssignStatement("v", new ValueExpression(new IntValue(3)))
                                                ),
                                                new PrintStatement(
                                                        new VariableExpression("v")
                                                )
                                        )
                                )
                        )
                );
    }

    public static IStatement createExample4() {
        return new CompoundStatement(
                        new VariableDeclarationStatement("varf", new StringType()),
                        new CompoundStatement(
                                new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                                new CompoundStatement(
                                        new OpenReadFileStatement(new VariableExpression("varf")),
                                        new CompoundStatement(
                                                new VariableDeclarationStatement("varc", new IntType()),
                                                new CompoundStatement(
                                                        new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("varc")),
                                                                new CompoundStatement(
                                                                        new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                        new CompoundStatement(
                                                                                new PrintStatement(new VariableExpression("varc")),
                                                                                new CloseReadFileStatement(new VariableExpression("varf"))
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                );
    }

    public static IStatement createExample5() {
        return new CompoundStatement(
            new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
            new CompoundStatement(
                new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                new CompoundStatement(
                    new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                    new CompoundStatement(
                        new AllocateStatement("a", new VariableExpression("v")),
                        new CompoundStatement(
                            new PrintStatement(new VariableExpression("v")),
                            new PrintStatement(new VariableExpression("a"))
                        )
                    )
                )
            )
        );
    }

    public static IStatement createExample6()
    {
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new AllocateStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                                new PrintStatement(new ArithmeticExpression('+',
                                                        new ReadHeapExpression(new ReadHeapExpression(new VariableExpression("a"))),
                                                        new ValueExpression(new IntValue(5))
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement createExample7()
    {
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("v"))),
                                new CompoundStatement(
                                        new WriteHeapStatement("v", new ValueExpression(new IntValue(30))),
                                        new PrintStatement(new ArithmeticExpression(
                                                '+',
                                                new ReadHeapExpression(new VariableExpression("v")),
                                                new ValueExpression(new IntValue(5))
                                                ))
                                )
                        )
                )
        );
    }

    public static IStatement createExample8()
    {
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new AllocateStatement("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntType()))),
                                new CompoundStatement(
                                        new AllocateStatement("a", new VariableExpression("v")),
                                        new CompoundStatement(
                                                new AllocateStatement("v", new ValueExpression(new IntValue(30))),
                                                new PrintStatement(new ReadHeapExpression(
                                                        new ReadHeapExpression(new VariableExpression("a"))
                                                ))
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement createExample9()
    {
        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType() ),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(5))),
                        new CompoundStatement(
                                new WhileStatement(
                                        new RelationalExpression(
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(0)),
                                                ">"
                                        ),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignStatement("v", new ArithmeticExpression(
                                                        '-',
                                                        new VariableExpression("v"),
                                                        new ValueExpression(new IntValue(1))
                                                ))
                                        )
                                ),
                                new PrintStatement(new VariableExpression("v"))
                        )
                )
        );
    }

    public static IStatement createThreadExample()
    {
        IStatement forkStatement = new ForkStatement(new CompoundStatement(
                new WriteHeapStatement("a", new ValueExpression(new IntValue(30))),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(32))),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("v")),
                                new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                        )
                )
            )
        );

        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                        new CompoundStatement(
                                new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(
                                        new AllocateStatement("a", new ValueExpression(new IntValue(22))),
                                        new CompoundStatement(
                                                forkStatement,
                                                new CompoundStatement(
                                                        new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement createSleepStatementExample()
    {
        //v=10;
        //(fork(v=v-1;v=v-1;print(v)); sleep(10);print(v*10)
        // will return above statement

        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(10))),
                        new CompoundStatement(
                                new ForkStatement(
                                        new CompoundStatement(
                                                new AssignStatement("v",
                                                    new ArithmeticExpression(
                                                        '-',
                                                        new VariableExpression("v"),
                                                        new ValueExpression(new IntValue(1))
                                                    )
                                                ),
                                                new CompoundStatement(
                                                        new AssignStatement("v",
                                                                new ArithmeticExpression(
                                                                '-',
                                                                new VariableExpression("v"),
                                                                new ValueExpression(new IntValue(1))
                                                            )
                                                        ),
                                                        new PrintStatement(new VariableExpression("v"))
                                                )
                                        )
                                ),
                                new CompoundStatement(
                                        new SleepStatement(10),
                                        new PrintStatement(
                                                new ArithmeticExpression(
                                                '*',
                                                new VariableExpression("v"),
                                                new ValueExpression(new IntValue(10))
                                                )
                                        )
                                )
                        )
                )
        );
    }

    public static IStatement createForkExample()
    {
        IStatement innerStatement = new CompoundStatement(
                new ForkStatement(
                    new ForkStatement(
                        new CompoundStatement(
                            new VariableDeclarationStatement("a", new IntType()),
                            new CompoundStatement(
                                    new AssignStatement("a", new VariableExpression("counter")),
                                    new PrintStatement(new VariableExpression("a"))
                            )
                        )
                    )
                ),
                new AssignStatement("counter", new ArithmeticExpression(
                        '+',
                        new VariableExpression("counter"),
                        new ValueExpression(new IntValue(1))
                    )
                )
        );

        return new CompoundStatement(
                new VariableDeclarationStatement("counter", new IntType()),
                    new WhileStatement(new RelationalExpression(
                            new VariableExpression("counter"),
                            new ValueExpression(new IntValue(10)),
                            "<"
                    ),
                     innerStatement
                )
        );
    }

    public static IStatement createExample10()
    {
        return new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new CompoundStatement(
                        new IfStatement(
                                new ValueExpression(new BoolValue(true)),
                                new VariableDeclarationStatement("b", new BoolType()),
                                new NOPStatement()
                        ),
                        new VariableDeclarationStatement("b", new IntType())
                )
        );
    }

    public static IStatement createTypeCheckerFailExample()
    {
        return new CompoundStatement(
                new VariableDeclarationStatement("a", new IntType()),
                new AssignStatement("a", new ValueExpression(new StringValue("salut")))
        );
    }

    public static IStatement createConditionalAssignmentExample() {
        IStatement variablesDeclarationStatement = new CompoundStatement(
            new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
            new CompoundStatement(
                new VariableDeclarationStatement("b", new ReferenceType(new IntType())),
                new CompoundStatement(
                    new VariableDeclarationStatement("v", new IntType()),
                    new CompoundStatement(
                        new AllocateStatement("a", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(
                            new AllocateStatement("b", new ValueExpression(new IntValue(0))),
                            new CompoundStatement(
                                new WriteHeapStatement("a", new ValueExpression(new IntValue(1))),
                                new WriteHeapStatement("b", new ValueExpression(new IntValue(2)))
                            )
                        )
                    )
                )
            )
        );

        return new CompoundStatement(
            variablesDeclarationStatement,
            new CompoundStatement(
                new ConditionalAssignmentStatement(
                    "v",
                    new RelationalExpression(
                        new ReadHeapExpression(new VariableExpression("a")),
                        new ReadHeapExpression(new VariableExpression("b")),
                        "<"
                    ),
                    new ValueExpression(new IntValue(100)),
                    new ValueExpression(new IntValue(200))
                ),
                new CompoundStatement(
                    new PrintStatement(new VariableExpression("v")),
                    new CompoundStatement(
                        new ConditionalAssignmentStatement(
                            "v",
                            new RelationalExpression(
                                new ArithmeticExpression(
                                    '-',
                                    new ReadHeapExpression(new VariableExpression("b")),
                                    new ValueExpression(new IntValue(2))
                                ),
                                new ReadHeapExpression(new VariableExpression("a")),
                                ">"
                            ),
                            new ValueExpression(new IntValue(100)),
                            new ValueExpression(new IntValue(200))
                        ),
                        new PrintStatement(new VariableExpression("v"))
                    )
                )
            )
        );
    }
    // For statement example
    public static IStatement createForStatementExample()
    {
        IStatement innerStatement = new ForkStatement(
                new CompoundStatement(
                        new PrintStatement(new VariableExpression("v")),
                        new AssignStatement("v",
                                new ArithmeticExpression(
                                        '*',
                                        new VariableExpression("v"),
                                        new ReadHeapExpression(new VariableExpression("a"))
                                )
                        )
                )
        );

        return new CompoundStatement(
                new VariableDeclarationStatement("a", new ReferenceType(new IntType())),
                new CompoundStatement(
                        new AllocateStatement("a", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(
                                new VariableDeclarationStatement("v", new IntType()),
                                new CompoundStatement(
                                        new ForStatement(
                                                "v",
                                                new ValueExpression(new IntValue(0)),
                                                new ValueExpression(new IntValue(3)),
                                                new ArithmeticExpression(
                                                        '+',
                                                        new VariableExpression("v"),
                                                        new ValueExpression(new IntValue(1))
                                                ),
                                                innerStatement
                                        ),
                                        new PrintStatement(new ReadHeapExpression(new VariableExpression("a")))
                                )
                        )
                )
        );
    }
    public static IStatement createSwitchExample()
    {
        IStatement variablesInitializationStatement =
                new CompoundStatement(
                    new VariableDeclarationStatement("a", new IntType()),
                    new CompoundStatement(
                        new AssignStatement("a", new ValueExpression(new IntValue(1))),
                        new CompoundStatement(
                            new VariableDeclarationStatement("b", new IntType()),
                            new CompoundStatement(
                                new AssignStatement("b", new ValueExpression(new IntValue(2))),
                                new CompoundStatement(
                                        new VariableDeclarationStatement("c", new IntType()),
                                        new AssignStatement("c", new ValueExpression(new IntValue(5)))
                                )
                            )
                        )
                    )
                );

        IStatement firstCaseStatement = new CompoundStatement(
                new PrintStatement(new VariableExpression("a")),
                new PrintStatement(new VariableExpression("b"))
        );

        IStatement secondCaseStatement = new CompoundStatement(
                new PrintStatement(new ValueExpression(new IntValue(100))),
                new PrintStatement(new ValueExpression(new IntValue(200)))
        );

        IStatement defaultStatement = new PrintStatement(new ValueExpression(new IntValue(300)));

        return new CompoundStatement(
                variablesInitializationStatement,
                new CompoundStatement(
                        new SwitchStatement(
                                new ArithmeticExpression(
                                        '*',
                                        new VariableExpression("a"),
                                        new ValueExpression(new IntValue(10))
                                ),
                                new ArithmeticExpression(
                                        '*',
                                        new VariableExpression("b"),
                                        new VariableExpression("c")
                                ),
                                new ValueExpression(new IntValue(10)),
                                firstCaseStatement,
                                secondCaseStatement,
                                defaultStatement
                        ),
                        new PrintStatement(new ValueExpression(new IntValue(300)))
                )
        );
    }

    public static IStatement createRepeatUntilExample()
    {
        IStatement innerStatement = new CompoundStatement(
                new ForkStatement(
                        new CompoundStatement(
                            new PrintStatement(new VariableExpression("v")),
                            new AssignStatement("v",
                                    new ArithmeticExpression('-',
                                            new VariableExpression("v"),
                                            new ValueExpression(new IntValue(1))))
                        )
                ),
                new AssignStatement("v",
                        new ArithmeticExpression('+',
                                new VariableExpression("v"),
                                new ValueExpression(new IntValue(1))
                        )
                )
        );

        return new CompoundStatement(
                new VariableDeclarationStatement("v", new IntType()),
                new CompoundStatement(
                        new AssignStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(
                            new RepeatUntilStatement(
                                    innerStatement,
                                    new RelationalExpression(
                                            new VariableExpression("v"),
                                            new ValueExpression(new IntValue(3)),
                                            "=="
                                    )
                            ),
                            new PrintStatement(
                                    new ArithmeticExpression(
                                            '*',
                                            new VariableExpression("v"),
                                            new ValueExpression(new IntValue(10))
                                    )
                            )
                        )
                )
        );
    }


    public static List<IStatement> getAllExamples()
    {
        ArrayList<IStatement> allStatements = new ArrayList<>();

        allStatements.add(createExample1());
        allStatements.add(createExample2());
        allStatements.add(createExample3());
        allStatements.add(createExample4());
        allStatements.add(createExample5());
        allStatements.add(createExample6());
        allStatements.add(createExample7());
        allStatements.add(createExample8());
        allStatements.add(createExample9());
        allStatements.add(createThreadExample());
        allStatements.add(createForkExample());
        allStatements.add(createExample10());
        allStatements.add(createTypeCheckerFailExample());
        allStatements.add(createConditionalAssignmentExample());
        allStatements.add(createForStatementExample());
        allStatements.add(createSleepStatementExample());
        allStatements.add(createSwitchExample());
        allStatements.add(createRepeatUntilExample());

        return allStatements;
    }
}
