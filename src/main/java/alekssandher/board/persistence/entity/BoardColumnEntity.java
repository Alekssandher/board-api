package alekssandher.board.persistence.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
public class BoardColumnEntity {
    private Long id;
    private String name;
    private int order;
    private BoardColumnEnum kind;

    @JsonBackReference
    private BoardEntity board = new BoardEntity();
}
