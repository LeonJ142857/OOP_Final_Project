package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class CellController extends ListCell<Task> {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label dateLabel;

    @FXML
    private ImageView iconImageView;

    @FXML
    private Label taskLabel;

    @FXML
    private Label descriptionLabel;

    private FXMLLoader fxmlLoader;

    @FXML
    private ImageView deleteButton;

    @FXML
    private ImageView listUpdateButton;

    @FXML
    private Label deadlineLabel;

    private DatabaseHandler databaseHandler;
    @FXML
    void initialize() {

    }

    @Override
    public void updateItem(Task myTask, boolean empty) {
        super.updateItem(myTask, empty);
        if(empty || myTask == null){
            setText(null);
            setGraphic(null);
        } else{
            if(fxmlLoader == null){
                fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
                fxmlLoader.setController(this);

                try {
                    fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            UpdateTaskController updateTaskController = loader.getController();
            taskLabel.setText(myTask.getTask());
            descriptionLabel.setText(myTask.getDescription());
            dateLabel.setText(myTask.getDateCreated().toString());
            deadlineLabel.setText(myTask.getDeadline());

            listUpdateButton.setOnMouseClicked(event -> {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/updateTask.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));

                UpdateTaskController updateTaskController = loader.getController();
                updateTaskController.setUpdateTaskField(myTask.getTask());
                updateTaskController.setUpdateDescriptionField(myTask.getDescription());
                updateTaskController.setUpdateDeadlineField(myTask.getDeadline());

                updateTaskController.updateTaskButton.setOnAction(event1 -> {
                    Calendar calendar = Calendar.getInstance();
                    java.sql.Timestamp timestamp =
                            new java.sql.Timestamp(calendar.getTimeInMillis());
                    try {
                        System.out.println("taskid " + myTask.getTaskId());
                        databaseHandler = new DatabaseHandler();
                        databaseHandler.updateTask(
                                updateTaskController.getTask(),
                                updateTaskController.getDescription(),
                                updateTaskController.getDeadline(),
                                timestamp,  myTask.getTaskId()
                        );
                    } catch (SQLException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
                stage.show();
            });

            deleteButton.setOnMouseClicked(event->{
                DatabaseHandler databaseHandler = new DatabaseHandler();
                try {
                    databaseHandler.deleteTask(Task.userId, myTask.getTaskId());
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
                getListView().getItems().remove(getItem());
            });
            setText(null);
            setGraphic(rootAnchorPane);
        }

    }
}
