package bank.exceptions;


/**
 * Transfer Amount Exception
 */
public class TransferAmountException extends Exception{
    /**
     * Standard constructor
     */
    public TransferAmountException()
    {
        super("Wrong Input, Amount has to be Positive");
    }
}
