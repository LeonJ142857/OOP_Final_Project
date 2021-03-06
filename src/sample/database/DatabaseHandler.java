package sample.database;

import sample.model.Task;
import sample.model.User;

import java.sql.*;

public class DatabaseHandler{
    private Connection dbConnection;

    public Connection getDBConnection() throws ClassNotFoundException, SQLException {
        //gets connection with locally hosted database
        String connectionString = "jdbc:mysql://" + Const.dbHost + ":" + Const.dbPort + "/" + Const.dbName;
        Class.forName("com.mysql.jdbc.Driver");
        //authenticate with root username and password
        dbConnection = DriverManager.getConnection(connectionString, Const.dbUser, Const.dbPass);
        return dbConnection;
    }
    public void updateTask(String task, String description,
                           String deadline, Timestamp datecreated,
                           int taskId)
            throws SQLException, ClassNotFoundException {
        //catch exceptions when handling database

        //update data todo.task rows
        String query = "UPDATE " + Const.TASKS_TABLE + " SET "
                + Const.TASKS_NAME + "=?, " + Const.TASKS_DESCRIPTION + "=?, "
                + Const.TASKS_DEADLINE + "=?, " + Const.TASKS_DATE + "=?"
                + " WHERE " + Const.TASKS_ID + "=?"; //prepare the query command into a string

        //initialize the preparedStatement with query string after connection is established
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
        // set the first query variable to task
        preparedStatement.setString(1, task);
        // set the second query variable to description
        preparedStatement.setString(2, description);
        // set the third query variable to deadline
        preparedStatement.setString(3, deadline);
        // set the fourth query variable to datecreated
        preparedStatement.setTimestamp(4, datecreated);
        // set the fifth query variable to taskId
        preparedStatement.setInt(5, taskId);
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }
    //Write
    public void signUpUser(User user){
        //prepare the query command into a string
        //insert into todo.users with values defined later into firstname, lastname, username, password
        String insert = "INSERT INTO "+Const.USERS_TABLE+"("+Const.USERS_FIRSTNAME +","+Const.USERS_LASTNAME
                +","+Const.USERS_USERNAME+","+Const.USERS_PASSWORD+")" + "VALUES(?,?,?,?)";
        try {
            //initialize the preparedStatement with query string after connection is established
            PreparedStatement preparedStatement = getDBConnection().prepareStatement(insert);
            // set the first query variable to user's firstname
            preparedStatement.setString(1, user.getFirstName());
            // set the second query variable to user's lastname
            preparedStatement.setString(2, user.getLastName());
            // set the third query variable to user's username
            preparedStatement.setString(3, user.getUserName());
            // set the fourth query variable to user's password
            preparedStatement.setString(4, user.getPassword());
            //execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
    public ResultSet getUser(User user){
        ResultSet resultSet = null; //initialize resultSet to null

        if(!user.getUserName().equals("") || !user.getPassword().equals("")){ //if username and password is not empty
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " +
                    Const.USERS_USERNAME + "=?" + " AND " + Const.USERS_PASSWORD
                    + "=?"; // select all from users where username=user.UserName and password=user.password
            try {
                PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());
                resultSet = preparedStatement.executeQuery();
            } catch (SQLException | ClassNotFoundException throwables) {
                throwables.printStackTrace();
            }

        } else{
            System.out.println("Please enter your credentials.");
        }
        return resultSet;
    }

    public int getTasksCount(int userID) throws SQLException, ClassNotFoundException {
        String query = "SELECT COUNT(*) FROM " + Const.TASKS_TABLE + " WHERE " +
                Const.USERS_ID + "=?"; // select all from users where username=user.UserName and password=user.password

        PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
        preparedStatement.setInt(1, userID);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) return resultSet.getInt(1);

        return 0;
    }

    public ResultSet getTasksByUser(int userId) throws SQLException, ClassNotFoundException {
        ResultSet resultTasks = null; //set resultSet to null initially
        String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " +
                Const.USERS_ID + "=?"; // select all from tasks where userid=userid.UserName
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
        preparedStatement.setInt(1, userId);
        return preparedStatement.executeQuery();
    }
    public void insertTask(Task task){
        // SQL commmand for inserting a new task
        String insert = "INSERT INTO "+Const.TASKS_TABLE+"("+Const.USERS_ID
                +","+Const.TASKS_NAME +","+Const.TASKS_DESCRIPTION
                +","+Const.TASKS_DEADLINE+","+Const.TASKS_DATE+")"
                +"VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = getDBConnection().prepareStatement(insert);
            //set query variables
            preparedStatement.setInt(1, Task.userId);
            preparedStatement.setString(2, task.getTask());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getDeadline());
            preparedStatement.setTimestamp(5, task.getDateCreated());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException throwables) {//catch exceptions
            throwables.printStackTrace();
        }
    }
    //Delete Task
    public void deleteTask(int userId, int taskId) throws SQLException, ClassNotFoundException {
        //SQL command for deleting all rows in todo table which have a particular user id and task id
        String query = "DELETE FROM " + Const.TASKS_TABLE +
                " WHERE " + Const.USERS_ID + "=?" + " AND " + Const.TASKS_ID + "=?";
        PreparedStatement preparedStatement = getDBConnection().prepareStatement(query);
        //set query variables
        preparedStatement.setInt(1, userId);
        preparedStatement.setInt(2, taskId);
        //execute then close the statement
        preparedStatement.execute();
        preparedStatement.close();
    }

}
