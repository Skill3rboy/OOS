package bank.exceptions;

/**
 * Account already Exists Exception
 */
public class AccountAlreadyExistsException extends Exception{

    /**
     * Standard constructor
     */
    public AccountAlreadyExistsException(){
        super("The Account already exists!");
    }
}
