package alekssandher.board.dto;

import alekssandher.board.persistence.entity.BoardColumnEnum;

public record BoardColumnDto(Long id, String name, int order, BoardColumnEnum kind, int cardsAmount) {
    
}
