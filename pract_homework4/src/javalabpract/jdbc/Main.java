package javalabpract.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static String URL = "jdbc:postgresql://localhost:5432/java_lab_pract_2020";
    private static String USER = "postgres";
    private static String PASSWORD = "07072001";

    public static void main(String[] args) throws SQLException {

            SimpleDataSource dataSource = new SimpleDataSource();
            Connection connection = dataSource.openConnection(URL, USER, PASSWORD);
            Statement statement = dataSource.openStatement(connection);

            ResultSet resultSet = statement.executeQuery("select * from student");
            while (resultSet.next()){
                System.out.println("ID " + resultSet.getInt("id"));
                System.out.println("First Name " + resultSet.getString("first_name"));
                System.out.println("Last Name " + resultSet.getString("last_name"));
                System.out.println("Age " + resultSet.getInt("age"));
                System.out.println("Group Number " + resultSet.getInt("group_number"));
            }

            resultSet.close();
            statement.close();
            connection.close();
    }
}
