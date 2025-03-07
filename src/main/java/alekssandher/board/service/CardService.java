package alekssandher.board.service;

import static alekssandher.board.persistence.entity.BoardColumnEnum.FINAL;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import alekssandher.board.dto.board.BoardColumnInfoDto;
import alekssandher.board.dto.card.CardDetailsDto;
import alekssandher.board.exception.exceptions.Exceptions.*;
import alekssandher.board.persistence.dao.CardDao;
import alekssandher.board.persistence.entity.CardEntity;

@Service
public class CardService {
    private final Connection connection;

    public CardService(Connection connection)
    {
        this.connection = connection;
    }

    public void moveCard(final Long cardId, final List<BoardColumnInfoDto> boardColumnsInfo) throws SQLException, NotFoundException, BadRequestException
    {
        try {
            
            CardDao dao = new CardDao(connection);
            Optional<CardDetailsDto> optional = dao.findByID(cardId);
            
            CardDetailsDto dto = optional.orElseThrow(() -> new NotFoundException("The card with id: %s was not found.".formatted(cardId)));
            
            BoardColumnInfoDto currentColumn = boardColumnsInfo.stream()
                        .filter(bc -> bc.id().equals(dto.columnId()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("The card provided doesn't belong to this board."));
            
            if(currentColumn.kind().equals(FINAL))
            {
                throw new BadRequestException("The card provided is finalized.");
            }
            BoardColumnInfoDto nextColumn = boardColumnsInfo.stream()
                .filter(bc -> bc.order() == currentColumn.order() + 1 )
                .findFirst()
                .orElseThrow();

            dao.moveToColumn(nextColumn.id(), cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
    public Optional<CardEntity> insert(final CardEntity cardEntity) throws SQLException
    {
        try {
            
            CardDao dao = new CardDao(connection);
            dao.insert(cardEntity);
            connection.commit();

            return Optional.ofNullable(cardEntity);
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
    public Optional<CardDetailsDto> findById(final Long id) throws SQLException
    {
        CardDao dao = new CardDao(connection);

        return dao.findByID(id);
    }

    public void cancel(final Long cardId, final Long cancelColumnId) throws SQLException, NotFoundException, ForbiddenException
    {
        try {
            
            CardDao dao = new CardDao(connection);
            Optional<CardDetailsDto> optional = dao.findByID(cardId);
            
            CardDetailsDto dto = optional.orElseThrow(() -> new NotFoundException("The card with id: %s was not found.".formatted(cardId)));
            
            if (dto.blocked())
            {
                String message = "The card: %s is blocked and can't be moved, unblock it first.".formatted(cardId);

                throw new ForbiddenException(message);
            }

            dao.moveToColumn(cancelColumnId, cardId);
            connection.commit();
        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }


}
