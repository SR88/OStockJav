package otherChallenges;

import java.sql.*;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

public class SqlProblem {

    // This would obviously not be setup here but in a config server or database
    static final String DB = "jdbc:postgresql://localhost:5432/ostock_dev";
    static final String USER = "postgres";
    static final String PW = "postgres";

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet rs;

    // Example 1 sanitize sql
    public void selectUserDetails(String userId, String password){
        Properties props = new Properties();
        props.setProperty("user", USER);
        props.setProperty("password", PW);
        try {
            connection = DriverManager.getConnection(DB, props);
            connection.setSchema("Users");

            // The password is stored in base64 string which is not secured
            String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());

            // Defend Against SQL Injection - I believe is the main point of the exercise
            String stringSelection = "select * from USERS where USERID = ? and PASSWORD = ?";

            preparedStatement = connection.prepareStatement(stringSelection);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, password);

            rs = preparedStatement.executeQuery();

            // Do business logic for whatever reason here

        } catch (SQLException sqlException) {
            System.out.printf("%tT - %1$td, %1$tB %1$tY  %n" + sqlException.getMessage(), new Date());
        } finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (preparedStatement != null){
                try { preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

}
