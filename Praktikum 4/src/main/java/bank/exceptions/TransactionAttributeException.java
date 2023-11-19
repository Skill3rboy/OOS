package bank.exceptions;

/**
 * Transcation attribute Exception
 */
public class TransactionAttributeException extends Exception {
    /**
     * Standard constructor
     */
    public TransactionAttributeException () {
        super("Validation for attribute failed!");
    }
}
