package alekssandher.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import alekssandher.board.dto.BoardColumnDto;
import alekssandher.board.dto.BoardDetailsDto;
import alekssandher.board.persistence.dao.BoardColumnDao;
import alekssandher.board.persistence.dao.BoardDao;
import alekssandher.board.persistence.entity.BoardColumnEntity;
import alekssandher.board.persistence.entity.BoardEntity;

@Service
public class BoardService {
    private final Connection connection;

    public BoardService(Connection connection)
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
    public Optional<BoardDetailsDto> findByIdWithDetails(final Long id) throws SQLException
    {
        BoardDao boardDao = new BoardDao(connection);
        BoardColumnDao boardColumnDao = new BoardColumnDao(connection);

        Optional<BoardEntity> optional = boardDao.findById(id);

        
        if (optional.isPresent())
        {
            BoardEntity boardEntity = optional.get();
           
            List<BoardColumnDto> columns = boardColumnDao.findByIdWithDetails(boardEntity.getId());

            BoardDetailsDto boardDetailDto = new BoardDetailsDto(boardEntity.getId(), boardEntity.getName(), columns);
            return Optional.of(boardDetailDto);
        }
        
        return Optional.empty();
    }


    public BoardEntity insert(final BoardEntity entity) throws SQLException
    {
        BoardDao dao = new BoardDao(connection);
        BoardColumnDao columnDao = new BoardColumnDao(connection);
        try {

            dao.insert(entity);

            List<BoardColumnEntity> columns = entity.getBoardColumns().stream().map(c -> {
                c.setBoard(entity);
                return c;
            }).toList();

            for (BoardColumnEntity column : columns) {
                columnDao.insert(column);
            }
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }

        return entity;
    }
    public boolean delete(final long id) throws SQLException
    {
        BoardDao dao = new BoardDao(connection);

        try{
            if(!dao.exists(id)) return false;
            
            dao.delete(id);
            connection.commit();

            return true;
        }
        catch (SQLException ex)
        {
            connection.rollback();
            throw ex;
        }
    }
}
