package alekssandher.board;

import static alekssandher.board.persistence.config.ConnectionConfig.getConnection;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import alekssandher.board.persistence.migration.MigrationStrategy;

@SpringBootApplication
public class BoardApplication {

	public static void main(String[] args) throws SQLException{
		SpringApplication.run(BoardApplication.class, args);

		try(Connection connection = getConnection()){
			new MigrationStrategy(connection).executeMigration();
		}

		
	}

}
