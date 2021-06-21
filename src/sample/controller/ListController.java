package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import sample.animations.Shake;
import sample.database.Const;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class ListController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ListView<Task> taskList;
    @FXML
    private TextField listTaskField;
    @FXML
    private TextField listDescriptionField;
    @FXML
    private DatePicker listDeadlineField;
    @FXML
    private Button listAddTaskButton;
    @FXML
    private ImageView listRefreshButton;

    private DatabaseHandler databaseHandler;
    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        tasks = FXCollections.observableArrayList();

        System.out.println("User Id from Task: " + Task.userId);
        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(Task.userId);

        while (resultSet.next()) {
            String taskName = resultSet.getString(Const.TASKS_NAME);
            String description = resultSet.getString(Const.TASKS_DESCRIPTION);
            Timestamp dateCreated = resultSet.getTimestamp(Const.TASKS_DATE);
            String deadline = resultSet.getString(Const.TASKS_DEADLINE);

            Task task = new Task(taskName, description, deadline, dateCreated);
            task.setTaskId(resultSet.getInt(Const.TASKS_ID));
            tasks.add(task);
        }
        taskList.setItems(tasks);
        taskList.setCellFactory(CellController -> new CellController());

        listRefreshButton.setOnMouseClicked(event->{
            try {
                refreshList();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        });
        listAddTaskButton.setOnAction(event->{
            addNewTask();
            listTaskField.setText("");
            listDescriptionField.setText("");
            listDeadlineField.setValue(null);
        });
    }

    public void refreshList() throws SQLException, ClassNotFoundException{
        System.out.println("refreshList in ListCont called");

        refreshedTasks = FXCollections.observableArrayList();

        System.out.println("User Id from Task: " + Task.userId);
        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(Task.userId);

        while (resultSet.next()) {
            String taskName = resultSet.getString(Const.TASKS_NAME);
            String description = resultSet.getString(Const.TASKS_DESCRIPTION);
            String deadline = resultSet.getString(Const.TASKS_DEADLINE);
            Timestamp dateCreated = resultSet.getTimestamp(Const.TASKS_DATE);

            Task task = new Task(taskName, description, deadline, dateCreated);
            task.setTaskId(resultSet.getInt(Const.TASKS_ID));
            refreshedTasks.add(task);
        }
        taskList.setItems(refreshedTasks);
        taskList.setCellFactory(CellController -> new CellController());
    }
    public void addNewTask(){
        if(!listTaskField.getText().equals("")
                || !listDescriptionField.getText().equals("")
                || listDeadlineField.getValue() != null){
            String taskName = listTaskField.getText().trim();
            String description = listDescriptionField.getText().trim();
            String deadline = listDeadlineField.getValue().toString();
            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis());
            Task myNewTask = new Task(taskName, description, deadline, timestamp);
            databaseHandler.insertTask(myNewTask);
            try {
                initialize();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }
        } else{
            Shake listTaskFieldShaker = new Shake(listTaskField);
            Shake listDescriptionFieldShaker = new Shake(listDescriptionField);
            Shake listDeadlineFieldShaker = new Shake(listDeadlineField);
            listTaskFieldShaker.shake();
            listDescriptionFieldShaker.shake();
            listDeadlineFieldShaker.shake();
        }
    }
}

