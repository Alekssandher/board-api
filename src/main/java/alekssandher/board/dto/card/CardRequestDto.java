package alekssandher.board.dto.card;

import alekssandher.board.persistence.entity.CardEntity;

public class CardRequestDto {
    public Long boardColumnId;
    public String title;
    public String description;

    public CardEntity toEntity()
    {
        var entity = new CardEntity();
        entity.setTitle(this.title);
        entity.setDescription(this.description);
        entity.getBoardColumnEntity().setId(this.boardColumnId);
        return entity;
    }
}
