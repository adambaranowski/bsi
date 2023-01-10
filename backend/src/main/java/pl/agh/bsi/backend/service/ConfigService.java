package pl.agh.bsi.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ConfigService {
    private final DataSource dataSource;

    public static final Path CREATE_SQL = Paths.get("./src/main/resources/schema.sql");
    public static final Path DATA_SQL = Paths.get("./src/main/resources/data.sql");


    public void resetDatabase() {
        try {

            JdbcTemplate template = new JdbcTemplate(dataSource);

            String dropTable = "DROP TABLE tasks; DROP TABLE users;";

            String create = String.join("\n",
                    Files.readAllLines(CREATE_SQL));
            String data = String.join("\n",
                    Files.readAllLines(DATA_SQL));

            template.execute(dropTable + create);
            template.execute(data);

            System.out.println("\n \n DATABASE RESET \n \n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
