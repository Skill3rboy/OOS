package bank.exceptions;

/**
 * Transaction does not exists Exception
 */
public class TransactionDoesNotExistException extends Exception{
    /**
     * Standard constructor
     */
    public TransactionDoesNotExistException () {
        super("Transaction does not exist!");
    }
}
