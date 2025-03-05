package alekssandher.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import alekssandher.board.persistence.dao.BoardColumnDao;
import alekssandher.board.persistence.dao.BoardDao;
import alekssandher.board.persistence.entity.BoardEntity;

public class BoardQueryService {
    private final Connection connection;

    public BoardQueryService(Connection connection)
    {
        this.connection = connection;
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException
    {
        BoardDao boardDao = new BoardDao(connection);
        BoardColumnDao boardColumnDao = new BoardColumnDao(connection);

        Optional<BoardEntity> optional = boardDao.findById(id);

        if (optional.isPresent())
        {
            BoardEntity boardEntity = optional.get();
            boardEntity.setBoardColumns(boardColumnDao.findById(boardEntity.getId()));
            return Optional.of(boardEntity);
        }
        
        return Optional.empty();
    }
}
