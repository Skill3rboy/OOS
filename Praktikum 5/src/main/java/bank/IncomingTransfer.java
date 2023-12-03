package bank;

/**
 * Incoming Transfers
 */
public class IncomingTransfer extends Transfer {
    /** Standard Constructor
     * @param date
     * @param amount
     * @param description
     * @param sender
     * @param recipient
     */
    public IncomingTransfer(String date, double amount,String description, String sender, String recipient )
    {
        super(date, amount, description, sender, recipient);
    }

    /**
     * @param transfer
     */
    public IncomingTransfer(Transfer transfer){
        super(transfer);
    }
    /**Calculates the value of an account
     * @return amount of Money
     */
    @Override
    public double calculate(){
        return +super.calculate();
    }
}
