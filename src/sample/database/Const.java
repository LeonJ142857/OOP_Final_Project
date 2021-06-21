package sample.database;

public class Const {
    //Database Credentials
    public static final String dbHost = "localhost";
    public static final String dbPort = "3306";
    public static final String dbUser = "root";
    public static final String dbPass = "p429PBETGLpRXh";
    public static final String dbName = "todo";

    public static final String USERS_TABLE = "users";
    public static final String TASKS_TABLE = "tasks";
    
    //USERS column names
    public static final String USERS_ID = "userid";
    public static final String USERS_FIRSTNAME = "firstname";
    public static final String USERS_LASTNAME = "lastname";
    public static final String USERS_USERNAME = "username";
    public static final String USERS_PASSWORD = "password";

    //TASKS column names
    public static final String TASKS_ID = "taskid";
    public static final String TASKS_NAME = "task";
    public static final String TASKS_DATE = "datecreated";
    public static final String TASKS_DEADLINE = "datedeadline";
    public static final String TASKS_DESCRIPTION = "description";
}
