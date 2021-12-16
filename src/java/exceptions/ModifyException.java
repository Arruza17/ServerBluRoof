package exceptions;

/**
 * Exception that is thrown when the updated data goes wrong
 *
 * @author Ander Arruza
 */
public class ModifyException extends Exception {

    /**
     * Empty constructor of the ModifyException
     */
    public ModifyException() {
    }

    /**
     * Contructs an instance of ModifyException with the messgae below
     *
     * @param message the message
     */
    public ModifyException(String message) {
        super(message);
    }

}
