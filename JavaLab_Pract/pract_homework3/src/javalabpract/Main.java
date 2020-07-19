package javalabpract;
import java.sql.*;

public class Main {

    private static String URL = "jdbc:postgresql://localhost:5432/Homework_number_3";
    private static String USER = "postgres";
    private static String PASSWORD = "07072001";

    public static void main(String[] args) throws SQLException {

        SimpleDataSource dataSource = new SimpleDataSource();
        Connection connection = dataSource.openConnection(URL, USER, PASSWORD);
        Statement statement = dataSource.openStatement(connection);
        ResultSet resultSet = statement.executeQuery("select * from Company");

        while (resultSet.next()){
            System.out.println("ID " + resultSet.getInt("id_company"));
            System.out.println("NAME " + resultSet.getString("name"));
        }

        resultSet.close();
        statement.close();
        connection.close();
    }
}
