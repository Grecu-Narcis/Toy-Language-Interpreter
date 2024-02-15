package ubb.models.adts;

import ubb.models.statements.IStatement;
import ubb.business.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyProcedureTable implements MyIProcedureTable {
    private final Map<String, Pair<List<String>, IStatement>> procedureTable;

    public MyProcedureTable() {
        this.procedureTable = new HashMap<>();
    }

    @Override
    public boolean isDefined(String procedureName) {
        return this.procedureTable.containsKey(procedureName);
    }

    @Override
    public void add(String procedureName, Pair<List<String>, IStatement> procedure) {
        this.procedureTable.put(procedureName, procedure);
    }

    @Override
    public Map<String, Pair<List<String>, IStatement>> getContent() {
        return this.procedureTable;
    }

    @Override
    public Pair<List<String>, IStatement> get(String procedureName) {
        return this.procedureTable.get(procedureName);
    }
}
