package se.andolf.blackjack.api.exception;

/**
 * @author Thomas on 2017-04-08.
 */
public class GameException extends Throwable {

    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(Throwable cause) {
        super(cause);
    }
}
