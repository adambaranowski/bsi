package pl.agh.bsi.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.agh.bsi.backend.controller.dto.Task;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

    private static final String GET_USERS_TASKS_QUERY = "SELECT title, content FROM tasks WHERE owner='%s'";

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
            }
            return taskList;
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }
}
