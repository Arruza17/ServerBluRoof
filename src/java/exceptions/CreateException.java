package exceptions;

/**
 * Exception that is thrown if the data insertion goes wrong
 *
 * @author Ander Arruza
 */
public class CreateException extends Exception {

    /**
     * Empty constructor of the CreateException
     */
    public CreateException() {
    }

    /**
     * Contructs an instance of CreateException with the messgae below
     *
     * @param message the message
     */
    public CreateException(String message) {
        super(message);
    }

}
