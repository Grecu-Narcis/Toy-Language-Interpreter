package ubb.infrastructure;

import ubb.business.Pair;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.adts.MyIHeap;
import ubb.models.adts.MyISemaphore;
import ubb.models.adts.MyILatchTable;
import ubb.models.statements.IStatement;
import ubb.models.values.IValue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProgramsRepository implements IRepository {
    private List<ProgramState> programs;
    private final String logFilePath;

    public ProgramsRepository(String logFilePath) {
        this.programs = new ArrayList<>();
        this.logFilePath = logFilePath;

        // clear the log file
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath)));
            logFile.close();
        } catch (IOException ignored) {
        }
    }

    public ProgramsRepository(ProgramState program, String logFilePath) {
        this.programs.add(program);
        this.logFilePath = logFilePath;
    }

    @Override
    public void addProgram(ProgramState programToAdd) {
        this.programs.add(programToAdd);
    }

    @Override
    public List<ProgramState> getAllPrograms() {
        return this.programs;
    }

    @Override
    public void setProgramsList(List<ProgramState> newPrograms) {
        this.programs = newPrograms;
    }

    private void logExeStack(PrintWriter logFile, ProgramState currentProgram) {
        logFile.println("ExeStack:");

        ArrayList<IStatement> stackStatements = (ArrayList<IStatement>) currentProgram.getStackStatements();

        for (IStatement currentStatement : stackStatements)
            logFile.println(currentStatement.toString());

        logFile.println();
    }

    private void logSymbolTable(PrintWriter logFile, ProgramState currentProgram) {
        logFile.println("SymTable:");

        MyIDictionary<String, IValue> symbolTable = currentProgram.getSymbolTable();
        for (String key : symbolTable.getKeySet())
            logFile.println(key + " = " + symbolTable.get(key));

        logFile.println();
    }

    private void logOutput(PrintWriter logFile, ProgramState currentProgram) {
        logFile.println("Output:");

        List<IValue> outputList = currentProgram.getOutputList().getOutput();
        for (IValue currentValue : outputList)
            logFile.println(currentValue);

        logFile.println();
    }

    private void logFileTable(PrintWriter logFile, ProgramState currentProgram) {
        logFile.println("File Table:");

        MyIDictionary<String, BufferedReader> fileTable = currentProgram.getFileTable();
        for (String fileName : fileTable.getKeySet())
            logFile.println(fileName);

        logFile.println();
    }

    private void logHeapTable(PrintWriter logFile, ProgramState currentProgram) {
        logFile.println("Heap Table:");

        MyIHeap heapTable = currentProgram.getHeapTable();
        for (Integer address : heapTable.getContent().keySet())
            logFile.println(address + " -> " + heapTable.getValueAtAddress(address));

        logFile.println();
    }

    private void logSemaphoreTable(PrintWriter logFile, ProgramState currentProgram) {
        logFile.println("Semaphore Table:");

        MyISemaphore semaphoreTable = currentProgram.getSemaphoreTable();
        for (Integer address : semaphoreTable.getContent().keySet())
            logFile.println(address + " -> " + semaphoreTable.getSemaphoreWithKey(address));

        logFile.println();
    }

    private void logLatchTable(PrintWriter logFile, ProgramState currentProgram) {
        logFile.println("Latch Table:");

        MyILatchTable latchTable = currentProgram.getLatchTable();
        for (Integer address : latchTable.getContent().keySet())
            logFile.println(address + " -> " + latchTable.getValueAtAddress(address));

        logFile.println();
    }

    @Override
    public void logProgramState(ProgramState currentProgram) {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

            logFile.println("Id=" + currentProgram.getId());
            // LOG Exe Stack
            this.logExeStack(logFile, currentProgram);

            // LOG Symbol Table
            this.logSymbolTable(logFile, currentProgram);

            // LOG Output
            this.logOutput(logFile, currentProgram);

            // LOG File Table
            this.logFileTable(logFile, currentProgram);

            // LOG Heap Table
            this.logHeapTable(logFile, currentProgram);

            // LOG Semaphore Table
            this.logSemaphoreTable(logFile, currentProgram);

            // LOG Latch Table
            this.logLatchTable(logFile, currentProgram);

            logFile.close();
        } catch (IOException ignored) {
        }
    }


}
