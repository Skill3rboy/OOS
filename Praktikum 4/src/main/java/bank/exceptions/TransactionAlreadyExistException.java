package bank.exceptions;

/**
 * Transaction already Exists Exception
 */
public class TransactionAlreadyExistException extends Exception{

    /**
     * Standard constructor
     */
    public  TransactionAlreadyExistException() {
        super("Transaction already Exsits!");
    }
}
