package ubb.business.view_controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import ubb.business.InterpreterController;
import ubb.business.Pair;
import ubb.exceptions.InterpreterException;
import ubb.infrastructure.IRepository;
import ubb.infrastructure.ProgramsRepository;
import ubb.models.ProgramState;
import ubb.models.adts.*;
import ubb.models.statements.IStatement;
import ubb.models.values.IValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProgramController {
    private IStatement programStatement;
    private InterpreterController interpreterController;

    @FXML
    private TextField numberOfProgramStates;

    @FXML
    private TableView<Pair<Integer, IValue>> heapTableView;

    @FXML
    private TableColumn<Pair<Integer, IValue>, Integer> heapAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, IValue>, String> heapValueColumn;

    @FXML
    private ListView<IValue> outputListView;

    @FXML
    private ListView<String> fileTableView;

    @FXML
    private ListView<Integer> programStatesIdentifiersView;

    @FXML
    private ListView<String> executionStackView;

    @FXML
    private TableView<Pair<String, IValue>> symbolTableView;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symbolVariableColumn;

    @FXML
    private TableColumn<Pair<String, IValue>, String> symbolValueColumn;

    @FXML
    private TableView<Pair<String, String>> procedureTableView;

    @FXML
    private TableColumn<Pair<String, String>, String> procedureNameColumn;

    @FXML
    private TableColumn<Pair<String, String>, String> parametersBodyColumn;

    @FXML
    private TableView<ObservableList<Object>> semaphoreTableView;

    @FXML
    private TableColumn<ObservableList<Object>, Integer> semaphoreIndexColumn;

    @FXML
    private TableColumn<ObservableList<Object>, Integer> semaphoreValueColumn;

    @FXML
    private TableColumn<ObservableList<Object>, String> semaphoreListColumn;

    @FXML
    private TableView<Pair<Integer, Integer>> latchTableView;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> latchTableAddressColumn;

    @FXML
    private TableColumn<Pair<Integer, Integer>, Integer> latchTableValueColumn;

    @FXML
    private Button oneStepButton;

    @FXML
    private Button allStepsButton;

    @FXML
    private void initialize() {
        heapAddressColumn.setCellValueFactory(pair -> new SimpleIntegerProperty(pair.getValue().getFirst()).asObject());
        heapValueColumn.setCellValueFactory(pair -> new SimpleStringProperty(pair.getValue().getSecond().toString()));

        symbolVariableColumn.setCellValueFactory(pair -> new SimpleStringProperty(pair.getValue().getFirst()));
        symbolValueColumn.setCellValueFactory(pair -> new SimpleStringProperty(pair.getValue().getSecond().toString()));

        procedureNameColumn.setCellValueFactory(pair -> new SimpleStringProperty(pair.getValue().getFirst()));
        parametersBodyColumn.setCellValueFactory(pair -> new SimpleStringProperty(pair.getValue().getSecond()));

        semaphoreIndexColumn.setCellValueFactory(data -> new SimpleIntegerProperty((Integer)data.getValue().get(0)).asObject());
        semaphoreValueColumn.setCellValueFactory(data -> new SimpleIntegerProperty((Integer)data.getValue().get(1)).asObject());
        semaphoreListColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2).toString()));
        symbolVariableColumn.setCellValueFactory(pair -> new SimpleStringProperty(pair.getValue().getFirst()));
        symbolValueColumn.setCellValueFactory(pair -> new SimpleStringProperty(pair.getValue().getSecond().toString()));

        latchTableAddressColumn.setCellValueFactory(pair -> new SimpleIntegerProperty(pair.getValue().getFirst()).asObject());
        latchTableValueColumn.setCellValueFactory(pair -> new SimpleIntegerProperty(pair.getValue().getSecond()).asObject());
    }

    public void setProgramStatement(IStatement programStatement) {
        this.programStatement = programStatement;
        this.numberOfProgramStates.setText(programStatement.toString());

        ProgramState currentProgram = new ProgramState(programStatement);
        IRepository programRepository = new ProgramsRepository("log.txt");
        programRepository.addProgram(currentProgram);
        this.interpreterController = new InterpreterController(programRepository);

        this.populate();
    }

    @FXML
    private void handleOneStepButton() {
        if (this.interpreterController.getAllPrograms().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing to execute!", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        try {
            this.interpreterController.oneStepAll();
            populate();
        } catch (InterpreterException e) {
            populate();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAllStepsButton() {
        if (this.interpreterController.getAllPrograms().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Nothing to execute!", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
            return;
        }

        try {
            this.interpreterController.allSteps();
            populate();
        } catch (InterpreterException e) {
            populate();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.showAndWait();
        }
    }

    @FXML
    private void populate() {
        this.populateHeap();
        this.populateOutputList();
        this.populateFileTableList();
        this.populateProgramStatesIdentifiers();
        this.populateSymbolTableView();
        this.populateExecutionStack();
        this.populateProcedureTable();
        this.populateSemaphoreTable();

        if (interpreterController.getAllPrograms().isEmpty())
        {
            this.oneStepButton.setDisable(true);
            this.allStepsButton.setDisable(true);
        }
    }

    private void populateProcedureTable()
    {
        MyIProcedureTable currentProcedureTable = new MyProcedureTable();

        if (!interpreterController.getAllPrograms().isEmpty())
            currentProcedureTable = interpreterController.getAllPrograms().getFirst().getProcedureTable();

        List<Pair<String, String>> procedureTableList = new ArrayList<>();

        for (Map.Entry<String, Pair<List<String>, IStatement>> entry : currentProcedureTable.getContent().entrySet())
            procedureTableList.add(new Pair<>(entry.getKey(), entry.getValue().toString()));

        this.procedureTableView.setItems(FXCollections.observableArrayList(procedureTableList));
        this.procedureTableView.refresh();
        this.populateSemaphoreTable();
    }

    private void populateSemaphoreTable()
    {
        MyISemaphore currentSemaphore = new MySemaphore();

        if (!interpreterController.getAllPrograms().isEmpty())
            currentSemaphore = interpreterController.getAllPrograms().getFirst().getSemaphoreTable();

        List<ObservableList<Object>> semaphoreTableList = new ArrayList<>();

        for (Map.Entry<Integer, Pair<Integer, List<Integer>>> entry : currentSemaphore.getContent().entrySet())
        {
            ObservableList<Object> row = FXCollections.observableArrayList();
            row.add(entry.getKey());
            row.add(entry.getValue().getFirst());
            row.add(entry.getValue().getSecond().toString());
            semaphoreTableList.add(row);
        }

        this.semaphoreTableView.setItems(FXCollections.observableArrayList(semaphoreTableList));
        this.semaphoreTableView.refresh();
        this.populateLatchTable();
    }

    private void populateLatchTable()
    {
        MyILatchTable currentLatchTable = new MyLatchTable();

        if (!interpreterController.getAllPrograms().isEmpty())
            currentLatchTable = interpreterController.getAllPrograms().getFirst().getLatchTable();

        List<Pair<Integer, Integer>> latchTablePairs = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : currentLatchTable.getContent().entrySet())
            latchTablePairs.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.latchTableView.setItems(FXCollections.observableArrayList(latchTablePairs));
        this.latchTableView.refresh();
    }

    private void populateHeap() {
        MyIHeap currentHeap = new MyHeap();

        if (!interpreterController.getAllPrograms().isEmpty())
            currentHeap = interpreterController.getAllPrograms().getFirst().getHeapTable();

        List<Pair<Integer, IValue>> heapTablePairs = new ArrayList<>();

        for (Map.Entry<Integer, IValue> entry : currentHeap.getContent().entrySet())
            heapTablePairs.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.heapTableView.setItems(FXCollections.observableArrayList(heapTablePairs));
        this.heapTableView.refresh();
    }

    private void populateOutputList() {
        MyIList<IValue> outputList = new MyList<>();

        if (!interpreterController.getAllPrograms().isEmpty())
            outputList = interpreterController.getAllPrograms().getFirst().getOutputList();

        else if (interpreterController.getCopyProgram() != null)
            outputList = interpreterController.getCopyProgram().getOutputList();

        this.outputListView.setItems(FXCollections.observableArrayList(outputList.getOutput()));
        this.outputListView.refresh();
    }

    private void populateFileTableList() {
        List<String> files = new ArrayList<>();

        if (!interpreterController.getAllPrograms().isEmpty())
            files = new ArrayList<>(interpreterController.getAllPrograms().getFirst().getFileTable().getKeySet());

        else if (interpreterController.getCopyProgram() != null)
            files = new ArrayList<>(interpreterController.getCopyProgram().getFileTable().getKeySet());

        this.fileTableView.setItems(FXCollections.observableArrayList(files));
        this.fileTableView.refresh();
    }

    private void populateProgramStatesIdentifiers() {
        List<ProgramState> programStates = this.interpreterController.getAllPrograms();
        List<Integer> idList = programStates.stream()
            .map(ProgramState::getId)
            .collect(Collectors.toList());

        this.programStatesIdentifiersView.setItems(FXCollections.observableArrayList(idList));
        this.programStatesIdentifiersView.refresh();

        if (programStates.size() > 1)
            this.numberOfProgramStates.setText("There are: " + programStates.size() + " programs!");
        else
            this.numberOfProgramStates.setText("There is one program!");
    }

    private ProgramState getCurrentProgram() {
        if (this.interpreterController.getAllPrograms().isEmpty())
            return null;

        int selectedId = programStatesIdentifiersView.getSelectionModel().getSelectedIndex();

        if (selectedId == -1)
            return this.interpreterController.getAllPrograms().getFirst();

        return this.interpreterController.getAllPrograms().get(selectedId);
    }

    private void populateSymbolTableView() {
        ProgramState currentProgram = this.getCurrentProgram();
        List<Pair<String, IValue>> symbolTableList = new ArrayList<>();

        if (currentProgram != null)
            for (Map.Entry<String, IValue> entry : currentProgram.getSymbolTable().getContent().entrySet())
                symbolTableList.add(new Pair<>(entry.getKey(), entry.getValue()));

        this.symbolTableView.setItems(FXCollections.observableArrayList(symbolTableList));
        this.symbolTableView.refresh();
    }

    private void populateExecutionStack() {
        ProgramState currentProgram = this.getCurrentProgram();
        List<String> exeStackList = new ArrayList<>();

        if (currentProgram != null)
            for (IStatement currentStatement : currentProgram.getStackStatements())
                exeStackList.add(currentStatement.toString());

        this.executionStackView.setItems(FXCollections.observableArrayList(exeStackList));
        this.executionStackView.refresh();
    }
}
