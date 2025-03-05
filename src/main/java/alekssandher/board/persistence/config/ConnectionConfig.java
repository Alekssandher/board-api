package alekssandher.board.persistence.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConnectionConfig {

    @Bean
    public static Connection getConnection() throws SQLException {

        final String URL = EnvConfig.get("DB_URL");
        final String USER = EnvConfig.get("DB_USER");
        final String PASSWORD = EnvConfig.get("DB_PASSWORD");

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        connection.setAutoCommit(false);

        return connection;
    }
}
