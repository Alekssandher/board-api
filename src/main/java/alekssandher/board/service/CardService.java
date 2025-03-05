package alekssandher.board.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import alekssandher.board.dto.CardDetailsDto;
import alekssandher.board.persistence.dao.CardDao;

@Service
public class CardService {
    private final Connection connection;

    public CardService(Connection connection)
    {
        this.connection = connection;
    }

    public Optional<CardDetailsDto> findById(final Long id) throws SQLException
    {
        CardDao dao = new CardDao(connection);

        return dao.findByID(id);
    }
}
