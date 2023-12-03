package bank.exceptions;

/**
 * Incoming Interest Exception
 */
public class IncomingException extends Exception {
    /**
     * Standard constructor
     */
    public IncomingException(){
        super("Wrong Input, Incoming-Interest has to be between 0 and 1!");
    }
}
