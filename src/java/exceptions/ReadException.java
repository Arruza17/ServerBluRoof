package exceptions;

/**
 * Exception that is thrown when the data obtained is wrong
 *
 * @author Ander Arruza
 */
public class ReadException extends Exception {

    /**
     * Empty constructor of the ReadException
     */
    public ReadException() {
    }

    /**
     * Contructs an instance of ReadException with the messgae below
     *
     * @param message the message
     */
    public ReadException(String message) {
        super(message);
    }

}
