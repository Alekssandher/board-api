package alekssandher.board.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import alekssandher.board.dto.BoardColumnInfoDto;
import alekssandher.board.dto.CardDetailsDto;
import alekssandher.board.exception.dtos.CardFinishedException;
import alekssandher.board.exception.dtos.EntityNotFoundException;
import alekssandher.board.persistence.entity.CardEntity;
import alekssandher.board.service.CardService;

@RestController
@RequestMapping("/card")
public class CardController {
    private CardService service;

    public CardController(CardService service)
    {
        this.service = service;
    }

    @PatchMapping("/{cardId}")
    public ResponseEntity<String> moveCard(@PathVariable final Long cardId, @RequestBody final List<BoardColumnInfoDto> boardColumnsInfo) throws SQLException, EntityNotFoundException, CardFinishedException
    {
        
        service.moveCard(cardId, boardColumnsInfo);

        return ResponseEntity.ok("Moved");
    }

    @PatchMapping("/{cardId}/{cancelColumnId}")
    public ResponseEntity<String> cancelCard(@PathVariable final Long cardId, @PathVariable Long cancelColumnId) throws SQLException, EntityNotFoundException
    {
        service.cancel(cardId, cancelColumnId);

        return ResponseEntity.ok("Moved");
    }
    @PostMapping
    public ResponseEntity<CardEntity> create(@RequestBody final CardEntity cardEntity) throws SQLException
    {
        Optional<CardEntity> result = service.insert(cardEntity);
       
        return result.map(ResponseEntity::ok)
                           .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    @GetMapping("{id}")
    public ResponseEntity<Optional<CardDetailsDto>> findById(@PathVariable final long id) throws SQLException
    {
        Optional<CardDetailsDto> result = service.findById(id);
        if(!result.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }
}
