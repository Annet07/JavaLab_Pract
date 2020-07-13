package javalabpract.repositories;

import javalabpract.models.Mentor;
import javalabpract.models.Student;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class StudentsRepositoryJdbcImpl implements StudentsRepository {

    private Connection connection;
    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from student where id = ";
    // language=SQL
    private final static String SQL_UPDATE_BY_ID = "update student set first_name = ?, last_name = ?, age = ?, group_number = ? where id = ";
    // language=SQL
    private final static String SQL_INSERT = "insert into student(first_name, last_name, age, group_number) values(?, ?, ?, ?);";
    // language=SQL
    private  final static String SQL_SELECT_ALL = "select s.id, s.first_name, s.last_name, s.age, s.group_number, " +
                    "m.id as m_id, m.first_name as m_first_name, m.last_name as m_last_name from student s left join mentor m on s.id = m.student_id";
    // language=SQL
    private final static String SQL_SELECT_ALL_BY_AGE = SQL_SELECT_ALL + " where s.age = ";

    public StudentsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }


    @Override
    public List<Student> findAllByAge(int age) {

        String request = SQL_SELECT_ALL_BY_AGE + age;

        return findAllThroughSomething(request);

    }

    @Override
    public List<Student> findAll() {
        return findAllThroughSomething(SQL_SELECT_ALL);
    }

    @Override
    public Student findById(Long id) {

        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT_BY_ID + id);
            if (resultSet.next()){
                return new Student(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("group_number")
                );
            } else return null;
        } catch (SQLException e){
            throw new IllegalArgumentException(e);
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e){
                    // ignore
                }
            }
            if (statement != null){
                try {
                    statement.close();
                } catch (SQLException e){
                    //ignore
                }
            }
        }

    }

    @Override
    public void save(Student entity) {
        try  {
            PreparedStatement preparedStatement = preparedStatementMethod(entity, SQL_INSERT);
            preparedStatement.execute();
            ResultSet result = preparedStatement.getGeneratedKeys();
            if (result.next()) {
                entity.setId(result.getLong("id"));
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void update(Student entity) {
        try {
            String request = SQL_UPDATE_BY_ID + entity.getId();
            PreparedStatement preparedStatement = preparedStatementMethod(entity, request);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private List<Student> findAllThroughSomething(String request) {
        List<Student> students;
        try(Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(request)) {
            students = new LinkedList<>();
            while (result.next()) {
                long studentID = result.getLong("id");
                Student currentStudent = students.stream().
                        filter(student1 -> student1.getId() == studentID).findAny().orElse(null);
                if (currentStudent == null) {
                    currentStudent = getStudentFromSql(result, studentID);
                    students.add(currentStudent);
                }
                int index = students.indexOf(currentStudent);
                currentStudent = students.get(index);

                long mentorID;
                if ((mentorID = result.getLong("m_id")) != 0) {
                    Mentor mentor = new Mentor(
                            mentorID, result.getString("m_first_name"),
                            result.getString("m_last_name"));
                    currentStudent.setMentor(mentor);
                }
            }
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
        return students;
    }

    private Student getStudentFromSql(ResultSet result, long id) {
        try {
            return new Student(id, result.getString("first_name").trim(),
                    result.getString("last_name").trim(), result.getInt("age"),
                    result.getInt("group_number"));
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private PreparedStatement preparedStatementMethod(Student entity, String request) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            preparedStatement.setString(1, entity.getFirstName());
            preparedStatement.setString(2, entity.getLastName());
            preparedStatement.setInt(3, entity.getAge());
            preparedStatement.setInt(4, entity.getGroupNumber());
            return preparedStatement;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
