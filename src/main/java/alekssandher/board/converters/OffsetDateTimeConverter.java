package alekssandher.board.converters;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;
import static lombok.AccessLevel.PRIVATE;

@AllArgsConstructor(access = PRIVATE)
public final class OffsetDateTimeConverter {
    public static OffsetDateTime toOffsetDateTime(final Timestamp value)
    {
        return nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), UTC) : null;
    }
}
