package alekssandher.board.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;

@Data
public class BoardEntity {
    private Long id;
    private String name;

    @JsonManagedReference
    @ToString.Exclude
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();
}
