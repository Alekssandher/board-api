package alekssandher.board.exception.dtos;

public class CardFinishedException extends Exception {
    public CardFinishedException(String message)
    {
        super(message);
    }

    public CardFinishedException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
