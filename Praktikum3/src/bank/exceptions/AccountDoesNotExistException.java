package bank.exceptions;

/**
 * Account does not Exist Exception
 */
public class AccountDoesNotExistException extends Exception{
    /**
     * Standard constructor
     */
    public AccountDoesNotExistException () {
        super("Account does not Exist!");
    }
}
