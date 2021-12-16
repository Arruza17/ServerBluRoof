package exceptions;

/**
 * Exception that is thrown when the deleting the data goes wrong
 *
 * @author Ander Arruza
 */
public class DeleteException extends Exception {

    /**
     * Empty constructor of the DeleteException
     */
    public DeleteException() {
    }

    /**
     * Contructs an instance of DeleteException with the messgae below
     *
     * @param message the message
     */
    public DeleteException(String message) {
        super(message);
    }

}
