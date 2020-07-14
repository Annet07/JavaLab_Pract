package javalabpract;

import javalabpract.jdbc.SimpleDataSource;
import javalabpract.models.Student;
import javalabpract.repositories.StudentsRepository;
import javalabpract.repositories.StudentsRepositoryJdbcImpl;

import java.sql.*;

public class Main {
    private static String URL = "jdbc:postgresql://localhost:5432/java_lab_pract_2020";
    private static String USER = "postgres";
    private static String PASSWORD = "07072001";

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        StudentsRepository studentsRepository = new StudentsRepositoryJdbcImpl(connection);
        /*Student student = new Student(3, "Лёха", "Дьяконов", 38, 902);
        studentsRepository.save(student);
        studentsRepository.update(student);
        studentsRepository.findAllByAge(19);
        studentsRepository.findAllById(1);*/
        System.out.println(studentsRepository.findAll());
        connection.close();
    }
}
