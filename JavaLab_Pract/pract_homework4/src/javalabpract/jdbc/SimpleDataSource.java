package javalabpract.jdbc;

import java.sql.*;

public class SimpleDataSource {
    public Connection openConnection(String url, String user, String password){
        try {
            return DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }
    public Statement openStatement(Connection connection){
        try {
            return connection.createStatement();
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }
    public ResultSet openResultSet(Statement statement, String sql){
        try {
            return statement.executeQuery(sql);
        }
        catch (SQLException e){
            throw new IllegalArgumentException(e);
        }
    }
}
