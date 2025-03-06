package alekssandher.board.dto;

import alekssandher.board.persistence.entity.BoardColumnEnum;

public record BoardColumnInfoDto(Long id, int order, BoardColumnEnum kind) {
    
}
