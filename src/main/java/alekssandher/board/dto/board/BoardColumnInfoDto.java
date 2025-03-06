package alekssandher.board.dto.board;

import alekssandher.board.persistence.entity.BoardColumnEnum;

public record BoardColumnInfoDto(Long id, int order, BoardColumnEnum kind) {
    
}
