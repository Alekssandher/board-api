package alekssandher.board.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.jdbc.StatementImpl;

import alekssandher.board.dto.board.BoardColumnDto;
import alekssandher.board.persistence.entity.BoardColumnEntity;
import static alekssandher.board.persistence.entity.BoardColumnEnum.findByName;

public class BoardColumnDao {
    
    private final Connection connection;

    public BoardColumnDao(Connection connection)
    {
        this.connection = connection;
    }

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException
    {
        String sql = "INSERT INTO BOARDS_COLUMNS (name, `order`, kind, board_id) values (?, ?, ?, ?) ";

        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            int i = 1;

            statement.setString(i ++, entity.getName());
            statement.setInt(i ++, entity.getOrder());
            statement.setString(i ++, entity.getKind().name());
            statement.setLong(i ++, entity.getBoard().getId());
            statement.executeUpdate();

            if(statement instanceof StatementImpl impl) 
            {
                entity.setId(impl.getLastInsertID());
            }

            return entity;
        }
    }

    public List<BoardColumnEntity> findById(Long id) 
    {
        List<BoardColumnEntity> entities = new ArrayList<>();
        String sql = "SELECT id, name, `order`, kind FROM BOARDS_COLUMNS WHERE board_id = ? ORDER BY `order`";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next())
            {
                BoardColumnEntity boardColumnEntity = new BoardColumnEntity();

                boardColumnEntity.setId(resultSet.getLong("id"));
                boardColumnEntity.setName(resultSet.getString("name"));
                boardColumnEntity.setOrder(resultSet.getInt("order"));
                boardColumnEntity.setKind(findByName(resultSet.getString("kind")));
                
                entities.add(boardColumnEntity);
                
            }
            
            return entities;

        } catch (SQLException e) {
            
        }
        return null;
    }

    public List<BoardColumnDto> findByIdWithDetails(Long id) 
    {
        List<BoardColumnDto> dtos = new ArrayList<>();
        String sql =
                """
                SELECT bc.id,
                       bc.name,
                       bc.kind,
                       (SELECT COUNT(c.id)
                               FROM CARDS c
                              WHERE c.board_column_id = bc.id) cards_amount
                  FROM BOARDS_COLUMNS bc
                 WHERE board_id = ?
                 ORDER BY `order`;
                """;
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            while(resultSet.next())
            {
                BoardColumnDto dto = new BoardColumnDto(
                    resultSet.getLong("bc.id"),
                    resultSet.getString("bc.name"),
                    resultSet.getInt("bc.order"),
                    findByName(resultSet.getString("bc.kind")),
                    resultSet.getInt("cards_amount")
                );
                
                dtos.add(dto);
                
            }
            
            return dtos;

        } catch (SQLException e) {
            
        }
        return null;
    }
}
