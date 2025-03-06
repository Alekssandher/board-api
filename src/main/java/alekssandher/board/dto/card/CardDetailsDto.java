package alekssandher.board.dto.card;

import java.time.OffsetDateTime;

public record CardDetailsDto(
    Long id, 
    String title,
    String description,
    boolean blocked, 
    OffsetDateTime blockedAt,
    String blockReason,
    int blocksAmmount,
    Long columnId,
    String columnName
    ) {
    
}
