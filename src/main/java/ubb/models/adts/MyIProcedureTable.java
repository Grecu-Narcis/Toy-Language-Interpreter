package ubb.models.adts;

import ubb.models.statements.IStatement;
import ubb.business.view_controllers.Pair;
import java.util.List;
import java.util.Map;

public interface MyIProcedureTable {
    boolean isDefined(String procedureName);
    void add(String procedureName, Pair<List<String>, IStatement> procedure);
    Map<String, Pair<List<String>, IStatement>> getContent();
    Pair<List<String>, IStatement> get(String procedureName);
}
