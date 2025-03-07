package alekssandher.board.controller;

import java.sql.SQLException;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import alekssandher.board.dto.board.BoardRequestDto;
import alekssandher.board.dto.board.BoardResponseDto;
import alekssandher.board.dto.response.ApiResponseDto.CreatedResponse;
import alekssandher.board.dto.response.ApiResponseDto.NoContentResponse;
import alekssandher.board.dto.response.ApiResponseDto.OkResponse;
import alekssandher.board.exception.exceptions.Exceptions.InternalErrorException;
import alekssandher.board.exception.exceptions.Exceptions.NotFoundException;
import alekssandher.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    private BoardController(BoardService boardService)
    {
        this.boardService = boardService;
    }
    
    @GetMapping("{id}")
    public ResponseEntity<OkResponse<Optional<BoardResponseDto>>> findById(@PathVariable Long id, HttpServletRequest request) throws SQLException, NotFoundException
    {
        Optional<BoardResponseDto> result = boardService.findById(id);

        if(!result.isPresent()) throw new NotFoundException("We couldn't find your request.");

        return ResponseEntity.status(HttpStatus.OK).body(new OkResponse<>(request, "Success", "Board Found", result));
    }
    @PostMapping
    public ResponseEntity<CreatedResponse> create(@RequestBody BoardRequestDto dto, HttpServletRequest request) throws SQLException, InternalErrorException
    {
        BoardResponseDto result = boardService.insert(dto);

        if(result == null) throw new InternalErrorException("Something went wrong at our side.");

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreatedResponse(request));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<NoContentResponse> delete(@PathVariable Long id, HttpServletRequest request) throws SQLException, NotFoundException
    {
        boolean result = boardService.delete(id);

        if(result) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new NoContentResponse(request));
        else throw new NotFoundException("We couldn't find your request.");
    }
}
