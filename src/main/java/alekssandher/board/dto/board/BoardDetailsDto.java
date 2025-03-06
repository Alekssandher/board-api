package alekssandher.board.dto.board;

import java.util.List;

public record BoardDetailsDto (Long id, String name, List<BoardColumnDto> columns) {
    
}
