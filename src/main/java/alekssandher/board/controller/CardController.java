package alekssandher.board.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import alekssandher.board.dto.board.BoardColumnInfoDto;
import alekssandher.board.dto.card.CardDetailsDto;
import alekssandher.board.dto.card.CardRequestDto;
import alekssandher.board.dto.response.ApiResponseDto.*;
import alekssandher.board.exception.dto.ErrorResponses.BadRequest;
import alekssandher.board.exception.dto.ErrorResponses.Forbidden;
import alekssandher.board.exception.dto.ErrorResponses.InternalErrorCustom;
import alekssandher.board.exception.dto.ErrorResponses.NotFound;
import alekssandher.board.exception.exceptions.Exceptions.*;
import alekssandher.board.persistence.entity.CardEntity;
import alekssandher.board.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/card")
public class CardController {
    private final CardService service;

    public CardController(CardService service) {
        this.service = service;
    }
    
    // Documentation
    @Operation(summary = "Move a card", description = "Moves a card to a new board column.")
    @ApiResponse(responseCode = "200", description = "Card moved successfully",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = OkResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class)))
    @ApiResponse(responseCode = "404", description = "Card not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))

    @PatchMapping("/{cardId}")
    public ResponseEntity<OkResponse<String>> moveCard(
            @PathVariable final Long cardId,
            @RequestBody final List<BoardColumnInfoDto> boardColumnsInfo,
            HttpServletRequest request) throws SQLException, NotFoundException, BadRequestException {

        service.moveCard(cardId, boardColumnsInfo);
        return ResponseEntity.ok(new OkResponse<>(request, "Success", "Card Moved", "Moved"));
    }

    // Documentation
    @Operation(summary = "Cancel a card", description = "Cancels a card by moving it to a cancel column.")
    @ApiResponse(responseCode = "200", description = "Card canceled successfully",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = OkResponse.class)))
    @ApiResponse(responseCode = "403", description = "Forbidden operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class)))
    @ApiResponse(responseCode = "404", description = "Card not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))

    @PatchMapping("/{cardId}/{cancelColumnId}")
    public ResponseEntity<OkResponse<String>> cancelCard(
            @PathVariable final Long cardId,
            @PathVariable Long cancelColumnId,
            HttpServletRequest request) throws SQLException, NotFoundException, ForbiddenException {

        service.cancel(cardId, cancelColumnId);
        return ResponseEntity.ok(new OkResponse<>(request, "Success", "Card Canceled", "Canceled"));
    }

    // Documentation
    @Operation(summary = "Create a new card", description = "Creates a new card in the system.")
    @ApiResponse(responseCode = "201", description = "Card created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreatedResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))

    @PostMapping
    public ResponseEntity<CreatedResponse> create(
            @RequestBody final CardRequestDto dto,
            HttpServletRequest request) throws SQLException, InternalErrorException {

        Optional<CardEntity> result = service.insert(dto.toEntity());

        if (!result.isPresent()) {
            throw new InternalErrorException("Something went wrong at our side.");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse(request));
    }

    // Documentation
    @Operation(summary = "Find a card by ID", description = "Retrieves card details by its ID.")
    @ApiResponse(responseCode = "200", description = "Card found",
                 content = @Content(mediaType = "application/json", schema = @Schema(implementation = OkResponse.class)))
    @ApiResponse(responseCode = "404", description = "Card not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotFound.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalErrorCustom.class)))
    
    @GetMapping("{id}")
    public ResponseEntity<OkResponse<Optional<CardDetailsDto>>> findById(
            @PathVariable final long id,
            HttpServletRequest request) throws SQLException, NotFoundException {

        Optional<CardDetailsDto> result = service.findById(id);

        if (!result.isPresent()) {
            throw new NotFoundException("We did not find your request.");
        }

        return ResponseEntity.ok(new OkResponse<>(request, "Success", "Card Found", result));
    }
}
