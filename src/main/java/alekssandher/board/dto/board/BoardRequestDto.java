package alekssandher.board.dto.board;

import java.util.List;
import java.util.stream.Collectors;

import alekssandher.board.persistence.entity.BoardColumnEntity;
import alekssandher.board.persistence.entity.BoardEntity;
import lombok.Data;

@Data
public class BoardRequestDto {
    public String name;
    public List<BoardColumnDto> boardColumnsDto;

    public BoardEntity toEntity()
    {
        
        BoardEntity entity = new BoardEntity();
        entity.setName(this.getName());
        
        List<BoardColumnEntity> entities = boardColumnsDto.stream()
            .map(dto -> {
                BoardColumnEntity boardColumnEntity = new BoardColumnEntity();
                boardColumnEntity.setName(dto.getName());
                boardColumnEntity.setOrder(dto.getOrder());
                boardColumnEntity.setKind(dto.getKind());
                return boardColumnEntity;
            })
            .collect(Collectors.toList());
        
        entity.setBoardColumns(entities);
        return entity;
    }   
}
