package sample.model;

import java.sql.Timestamp;

public class Task {
    public static int userId;
    private int taskId;
    private String task;
    private String description;
    private String deadline;
    private Timestamp dateCreated;

    public Task() {
    }

    public Task(String task, String description, String deadline, Timestamp dateCreated) {
        this.description = description;
        this.task = task;
        this.deadline = deadline;
        this.dateCreated = dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
