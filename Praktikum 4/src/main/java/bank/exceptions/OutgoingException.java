package bank.exceptions;

/**
 * Outgoing Interest Excpetion
 */
public class OutgoingException extends Exception{
    /**
     * Standard Constructor
     */
    public OutgoingException(){
        super("Wrong Input, Outgoing-Interest has to be between 0 and 1!");
    }
}
