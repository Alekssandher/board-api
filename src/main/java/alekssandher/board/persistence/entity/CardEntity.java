package alekssandher.board.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
public class CardEntity {
    
    private Long id;
    private String title;
    private String description;

    @JsonBackReference
    private BoardColumnEntity boardColumnEntity = new BoardColumnEntity();
}
