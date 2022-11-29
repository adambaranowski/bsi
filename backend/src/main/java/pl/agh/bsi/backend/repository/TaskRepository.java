package pl.agh.bsi.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.agh.bsi.backend.controller.dto.Task;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private static final String GET_USERS_TASKS_QUERY = "SELECT title, content FROM tasks WHERE owner='%s'";
    private static final String ADD_TASK_QUERY = "INSERT INTO tasks (owner, title, content) VALUES ('%s', '%s', '%s')";
    private static final String TASK_BY_ID_QUERY = "SELECT title, content from tasks WHERE id='%d'";
    private final DataSource dataSource;

    public List<Task> getTasksByOwner(String owner){
        String sql = String.format(GET_USERS_TASKS_QUERY, owner);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            List<Task> taskList = new ArrayList<>();
            while (resultSet.next()) {
                Task task = new Task();
                task.setTitle(resultSet.getString("title"));
                task.setContent(resultSet.getString("content"));
                taskList.add(task);
            }
            return taskList;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    public Task getTaskById(Integer id) {
        String sql = String.format(TASK_BY_ID_QUERY, id);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            Task task = null;
            while (resultSet.next()) {
                task = new Task();
                task.setTitle(resultSet.getString("title"));
                task.setContent(resultSet.getString("content"));
            }
            return task;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public void addTaskByOwnerTitleContent(String owner, String title, String content) {
        String sql = String.format(ADD_TASK_QUERY, owner, title, content);
        try (
                Connection conn = dataSource.getConnection();
                Statement statement = conn.createStatement();
        ) {
            statement.execute(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
