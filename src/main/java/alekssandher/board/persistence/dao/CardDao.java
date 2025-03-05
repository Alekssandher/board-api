package alekssandher.board.persistence.dao;

import static java.util.Objects.nonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import alekssandher.board.dto.CardDetailsDto;

import static alekssandher.board.converters.OffsetDateTimeConverter.toOffsetDateTime;
public class CardDao {
    private Connection connection;

    public CardDao(Connection connection)
    {
        this.connection = connection;
    }

    public Optional<CardDetailsDto> findByID(final Long id) throws SQLException
    {
        String sql =
                """
                SELECT c.id,
                       c.title,
                       c.description,
                       b.blocked_at,
                       b.block_reason,
                       c.board_column_id,
                       bc.name,
                       (SELECT COUNT(sub_b.id)
                               FROM BLOCKS sub_b
                              WHERE sub_b.card_id = c.id) blocks_amount
                  FROM CARDS c
                  LEFT JOIN BLOCKS b
                    ON c.id = b.card_id
                   AND b.unblocked_at IS NULL
                 INNER JOIN BOARDS_COLUMNS bc
                    ON bc.id = c.board_column_id
                  WHERE c.id = ?;
                """;

        try(PreparedStatement statement = connection.prepareStatement(sql))
        {
            statement.setLong(1, id);
            statement.executeQuery();

            ResultSet resultSet = statement.getResultSet();

            if (resultSet.next())
            {
                CardDetailsDto dto = new CardDetailsDto(
                    resultSet.getLong("c.id"),
                    resultSet.getString("c.title"),
                    resultSet.getString("c.description"),
                    nonNull(resultSet.getString("b.blocked_reason")),
                    toOffsetDateTime(resultSet.getTimestamp("b.blocked_at")),
                    resultSet.getString("b.blocked_reason"),
                    resultSet.getInt("blocks_amount"),
                    resultSet.getLong("c.board_column_id"),
                    resultSet.getString("bc.name")
                    
                );

                return Optional.of(dto);
            }
        }
        return null;
    }
}
