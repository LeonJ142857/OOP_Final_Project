package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;

public class UpdateTaskController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField updateTaskField;

    @FXML
    private TextField updateDescriptionField;

    @FXML
    private DatePicker updateDeadlineField;

    @FXML
    public Button updateTaskButton;

    @FXML
    void initialize() {
    }

    public void setUpdateTaskField(String taskName) {
        updateTaskField.setText(taskName);
    }

    public void setUpdateDescriptionField(String description) {
        updateDescriptionField.setText(description);
    }

    public void setUpdateDeadlineField(String deadline) {
//        updateDeadlineField.setValue(new LocalDateStringConverter().fromString(deadline));
        updateDeadlineField.setValue(LocalDate.parse(deadline)); //parse date string
    }

    public String getTask(){
        return updateTaskField.getText().trim();
    }
    public String getDescription(){
        return updateDescriptionField.getText().trim();
    }
    public String getDeadline(){
        return updateDeadlineField.getValue().toString();
    }
    public Timestamp getDateCreated(){
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }
}
