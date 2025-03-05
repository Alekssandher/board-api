package alekssandher.board.controller;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.board.dto.CardDetailsDto;
import alekssandher.board.service.CardService;

@RestController
@RequestMapping("/card")
public class CardController {
    private CardService service;

    public CardController(CardService service)
    {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<CardDetailsDto>> findById(final long id) throws SQLException
    {
        Optional<CardDetailsDto> result = service.findById(id);
        if(!result.isPresent()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(result);
    }
}
