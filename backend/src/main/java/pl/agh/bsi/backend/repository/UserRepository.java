package pl.agh.bsi.backend.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@RequiredArgsConstructor
public class UserRepository {
    private static final String ADD_USER_QUERY = "INSERT INTO users (username, password) VALUES ('%s', '%s')";
    private static final String GET_USERS_PASSWORD_QUERY = "SELECT password FROM users WHERE username='%s'";

    private final DataSource dataSource;

    public String getUsersPassword(String username){
        String sql = String.format(GET_USERS_PASSWORD_QUERY, username);
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return null;
    }

    public void addNewUser(String username, String password){
        String sql = String.format(ADD_USER_QUERY, username, password);
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
