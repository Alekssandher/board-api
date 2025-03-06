package alekssandher.board.dto.board;

import alekssandher.board.persistence.entity.BoardColumnEnum;

public record BoardColumnDto(Long id, String name, int order, BoardColumnEnum kind, int cardsAmount) {
    
    public String getName()
    {
        return this.name;
    }

    public int getOrder()
    {
        return this.order;
    }

    public BoardColumnEnum getKind()
    {
        return this.kind;
    }
}
