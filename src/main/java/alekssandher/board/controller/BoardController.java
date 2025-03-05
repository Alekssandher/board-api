package alekssandher.board.controller;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.board.persistence.entity.BoardEntity;
import alekssandher.board.service.BoardService;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private BoardController(BoardService boardService)
    {
        this.boardService = boardService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Optional<BoardEntity>> findById(@PathVariable Long id) throws SQLException
    {
        Optional<BoardEntity> result = boardService.findById(id);

        if(!result.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }
    @PostMapping
    public ResponseEntity<BoardEntity> create(@RequestBody BoardEntity boardEntity) throws SQLException
    {
        BoardEntity result = boardService.insert(boardEntity);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws SQLException
    {
        boolean result = boardService.delete(id);

        if(result) return ResponseEntity.ok("Deleted");
        else return ResponseEntity.notFound().build();
    }
}
