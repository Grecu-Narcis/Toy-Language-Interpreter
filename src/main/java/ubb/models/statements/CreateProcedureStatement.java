package ubb.models.statements;

import ubb.business.Pair;
import ubb.exceptions.InterpreterException;
import ubb.models.ProgramState;
import ubb.models.adts.MyIDictionary;
import ubb.models.types.Type;

import java.util.List;

public class CreateProcedureStatement implements IStatement {
    //USAGE: procedure procedureName(parameter1, parameter2, ...) statement =>
    // new CreateProcedureStatement("procedureName", List.of("parameter1", "parameter2", ...), statement)
    // parameter1, parameter2, ... are strings

    private final String procedureName;
    private final IStatement procedureStatement;
    private final List<String> procedureParameters;

    public CreateProcedureStatement(String procedureName, List<String> procedureParameters, IStatement procedureStatement) {
        this.procedureName = procedureName;
        this.procedureParameters = procedureParameters;
        this.procedureStatement = procedureStatement;
    }

    @Override
    public ProgramState execute(ProgramState currentState) throws InterpreterException {
        currentState.getProcedureTable().add(
                this.procedureName,
                new Pair<>(this.procedureParameters, this.procedureStatement)
        );

        return null;
    }

    @Override
    public MyIDictionary<String, Type> typeCheck(MyIDictionary<String, Type> typeTable) throws InterpreterException {
        return typeTable;
    }

    @Override
    public String toString() {
        return "procedure " + this.procedureName + "(" + this.procedureParameters + ") {" + this.procedureStatement + "}";
    }
}
