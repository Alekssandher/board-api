
package alekssandher.board.persistence.entity;

import java.util.stream.Stream;

public enum BoardColumnEnum {
    INITIAL,
    FINAL,
    CANCEL,
    PENDING;

    public static BoardColumnEnum findByName(final String name)
    {
        return Stream.of(BoardColumnEnum.values())
            .filter(b -> b.name().equals(name))
            .findFirst().orElseThrow();
    }
}
