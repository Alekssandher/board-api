package alekssandher.board.persistence.entity;

import static alekssandher.board.persistence.entity.BoardColumnEnum.CANCEL;
import static alekssandher.board.persistence.entity.BoardColumnEnum.INITIAL;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import alekssandher.board.dto.board.BoardResponseDto;
import lombok.Data;

@Data
public class BoardEntity {
    private Long id;
    private String name;

    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    public BoardResponseDto toDto()
    {
        var dto = new BoardResponseDto(this.id, this.name);
        return dto;
    }
    public BoardColumnEntity getInitialColumn()
    {   
        return getFilteredColumn(bc -> bc.getKind().equals(INITIAL));
    }

    public BoardColumnEntity getCancelColumn()
    {
        return getFilteredColumn(bc -> bc.getKind().equals(CANCEL));
    }

    private BoardColumnEntity getFilteredColumn(Predicate<BoardColumnEntity> filter)
    {
        return boardColumns.stream()
            .filter(filter)
            .findFirst().orElseThrow();
    }


}
