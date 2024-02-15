package ubb.models;

import ubb.exceptions.InterpreterException;
import ubb.exceptions.StackException;
import ubb.models.adts.*;
import ubb.models.statements.CompoundStatement;
import ubb.models.statements.IStatement;
import ubb.models.values.IValue;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ProgramState {
    private MyIStack<IStatement> exeStack;
    private MyIStack<MyIDictionary<String, IValue>> symbolTable;
    private MyIList<IValue> outputList;
    private final MyIDictionary<String, BufferedReader> fileTable;
    private final MyIHeap heapTable;
    private final MyIProcedureTable procedureTable;
    private final int id;
    private static int currentID = 0;

    public ProgramState(IStatement program) {
        this.exeStack = new MyStack<>();
        this.symbolTable = new MyStack<>();
        this.symbolTable.push(new MyDictionary<>());
        this.outputList = new MyList<>();
        this.fileTable = new MyDictionary<>();
        this.heapTable = new MyHeap();
        this.procedureTable = new MyProcedureTable();
        this.exeStack.push(program);

        this.id = ProgramState.getAvailableId();
    }

    public ProgramState(MyIStack<IStatement> exeStack, MyIStack<MyIDictionary<String, IValue>> symbolTable,
                        MyIList<IValue> outputList, MyIDictionary<String, BufferedReader> fileTable,
                        MyIHeap heapTable, MyIProcedureTable procedureTable) {
        this.exeStack = exeStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heapTable = heapTable;
        this.procedureTable = procedureTable;

        this.id = ProgramState.getAvailableId();
    }

    public int getId() {
        return this.id;
    }

    private synchronized static int getAvailableId() {
        ProgramState.currentID += 1;
        return ProgramState.currentID;
    }

    public Boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public ProgramState executeOneStatement() throws InterpreterException {
        try {
            IStatement currentStatement = exeStack.pop();
            return currentStatement.execute(this);
        } catch (StackException e) {
            throw new InterpreterException("Statements stack is empty!");
        }
    }

    public MyIProcedureTable getProcedureTable() {
        return this.procedureTable;
    }

    public MyIStack<IStatement> getStack() {
        return this.exeStack;
    }

    public void setExeStack(MyIStack<IStatement> newStack) {
        this.exeStack = newStack;
    }

    public MyIDictionary<String, IValue> getSymbolTable() {
        return this.symbolTable.peek();
    }

    public MyIStack<MyIDictionary<String, IValue>> getSymbolTableStack() {
        return this.symbolTable;
    }

    public void addSymbolTable(MyIDictionary<String, IValue> newSymbolTable) {
        this.symbolTable.push(newSymbolTable);
    }

    public void popSymbolTable() throws InterpreterException {
        try {
            this.symbolTable.pop();
        } catch (StackException e) {
            throw new InterpreterException("Symbol table stack is empty!");
        }
    }

    public void setSymbolTable(MyIStack<MyIDictionary<String, IValue>> newSymbolTable) {
        this.symbolTable = newSymbolTable;
    }

    public MyIList<IValue> getOutputList() {
        return this.outputList;
    }

    public MyIDictionary<String, BufferedReader> getFileTable() {
        return this.fileTable;
    }

    public void setOutputList(MyList<IValue> newOutputList) {
        this.outputList = newOutputList;
    }

    public MyIHeap getHeapTable() {
        return this.heapTable;
    }

    public List<IStatement> getStackStatements() {
        ArrayList<IStatement> stackStatements = new ArrayList<>();

        if (exeStack.isEmpty())
            return stackStatements;

        Stack<IStatement> statements = new Stack<>();
        for (IStatement currentStatement : this.exeStack.getStackAsList())
            statements.push(currentStatement);

        while (!statements.isEmpty()) {
            IStatement topStatement = statements.pop();
            if (topStatement instanceof CompoundStatement currentStatement) {
                statements.push(currentStatement.getSecondStatement());
                statements.push(currentStatement.getFirstStatement());
            } else
                stackStatements.add(topStatement);
        }

        return stackStatements;
    }

    @Override
    public String toString() {
        return "PrgState{" +
            "exeStack=" + exeStack +
            ", symbolTable=" + symbolTable +
            ", outputList=" + outputList +
            ", FileTable=" + fileTable +
            '}';
    }
}
