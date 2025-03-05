package alekssandher.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.mysql.cj.jdbc.StatementImpl;

import alekssandher.board.persistence.entity.BoardEntity;

public class BoardDao {
    private final Connection connection;

    public BoardDao(Connection connection)
    {
        this.connection = connection;
    }

    
    public BoardEntity insert(final BoardEntity entity) throws SQLException
    {
        String sql = "INSERT INTO BOARDS (name) values (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.executeUpdate();

            if(preparedStatement instanceof StatementImpl impl)
            {
                entity.setId(impl.getLastInsertID());

            }
        }
            return entity;

    }

    public void delete(final Long id) throws SQLException
    {
        String sql = "DELETE FROM BOARDS WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException
    {
        String sql = "SELECT id, name FROM BOARDS WHERE ID = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();

            ResultSet resultSet =  preparedStatement.getResultSet();

            if(resultSet.next())
            {
                BoardEntity boardEntity = new BoardEntity();
                boardEntity.setId(resultSet.getLong("id"));
                boardEntity.setName(resultSet.getString("name"));

                return Optional.of(boardEntity);
            }

            return Optional.empty();

        }   
        
    }

    public boolean exists(final Long id) throws SQLException
    {
        String sql = "SELECT 1 FROM BOARDS WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();

            return preparedStatement.getResultSet().next();
        }
        

    }
}
