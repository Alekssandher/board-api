package alekssandher.board.persistence.migration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import liquibase.Liquibase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import static alekssandher.board.persistence.config.ConnectionConfig.getConnection;

public class MigrationStrategy {

    public final Connection connection;

    public MigrationStrategy(Connection connection) 
    {
        this.connection = connection;
    }

    public void executeMigration()
    {
        PrintStream originalOut = System.out;
        PrintStream originalErr = System.err;

        try 
        {
            FileOutputStream fos = new FileOutputStream("liquidbase.log");
            System.setOut(new PrintStream(fos));
            System.setErr(new PrintStream(fos));

            try(
                Connection connection = getConnection();
                JdbcConnection jdbcConnection = new JdbcConnection(connection);
            ) {

                try (Liquibase liquibase = new Liquibase("db/changeLog/db.changelog-master.yml", new ClassLoaderResourceAccessor(), jdbcConnection)) {
                    liquibase.update();
                }
            } 
            catch(SQLException |  LiquibaseException ex)
            {
                ex.printStackTrace();
            }

            System.setErr(originalErr);
        }
        catch (IOException ex)
        {   
            ex.printStackTrace();
        } 
        finally 
        {
            System.setOut(originalOut);
            System.setErr(originalErr);
        }
        
    }
}