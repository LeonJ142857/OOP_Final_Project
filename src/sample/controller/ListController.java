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

import static sample.HelperMethods.listShake;

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
        tasks = FXCollections.observableArrayList(); //initialize observableArrayList

//        System.out.println("User Id from Task: " + Task.userId);
        databaseHandler = new DatabaseHandler(); //initialize databaseHandler
        //getTasks from the database that have a particular user id
        ResultSet resultSet = databaseHandler.getTasksByUser(Task.userId);

        //loop through all the resultSet rows
        while (resultSet.next()) {
            //get all the data in each column
            String taskName = resultSet.getString(Const.TASKS_NAME);
            String description = resultSet.getString(Const.TASKS_DESCRIPTION);
            Timestamp dateCreated = resultSet.getTimestamp(Const.TASKS_DATE);
            String deadline = resultSet.getString(Const.TASKS_DEADLINE);

            //construct Task object
            Task task = new Task(taskName, description, deadline, dateCreated);
            //set the taskId
            task.setTaskId(resultSet.getInt(Const.TASKS_ID));
            //add the task object to tasks observableArrayList
            tasks.add(task);
        }
        //set the items of ListView taskList to observableList tasks
        taskList.setItems(tasks);
        //set new cell factory
        taskList.setCellFactory(CellController -> new CellController());

        //event handling when listRefreshButton is clicked
        //used when updating task data
        listRefreshButton.setOnMouseClicked(event->{
            try {
                refreshList();
            } catch (SQLException | ClassNotFoundException throwables) { //catch exceptions when refreshing list
                throwables.printStackTrace();
            }
        });
        //event handling when listAddTaskButton is clicked
        listAddTaskButton.setOnAction(event->{
            addNewTask();
            //empty all the text fields
            listTaskField.setText("");
            listDescriptionField.setText("");
            listDeadlineField.setValue(null);
        });
    }

    public void refreshList() throws SQLException, ClassNotFoundException{
        //create a new observableArrayList when updated,
        //so as to not overlap data with old observable array list
        //the functionalities are pretty much the same as initialize()
        //except we create a new observableArrayList
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
        // no field is empty
        if(!listTaskField.getText().equals("")
                || !listDescriptionField.getText().equals("")
                || listDeadlineField.getValue() != null){
            //trim all the input from the text fields and convert some to the correct type
            String taskName = listTaskField.getText().trim();
            String description = listDescriptionField.getText().trim();
            String deadline = listDeadlineField.getValue().toString();
            //get current time
            java.sql.Timestamp timestamp =
                    new java.sql.Timestamp(
                            Calendar.getInstance().getTimeInMillis());
            //create new Task object with the data as arguments
            Task myNewTask = new Task(taskName, description, deadline, timestamp);
            //insert the task to database
            databaseHandler.insertTask(myNewTask);
            try {
                //basically acts as auto refresh list so that the list is updated
                initialize();
            } catch (SQLException | ClassNotFoundException throwables) {
                //exception handling when initialize() throws error
                throwables.printStackTrace();
            }
        } else listShake(listTaskField, listDescriptionField, listDeadlineField);
    }
}

