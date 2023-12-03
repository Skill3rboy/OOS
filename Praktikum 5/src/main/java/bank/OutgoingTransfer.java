package bank;

/**
 * Outgoing Transfer
 */
public class OutgoingTransfer extends Transfer{
    /**Standard constructor for Outgoing Transfer
     * @param date
     * @param amount
     * @param description
     * @param sender
     * @param recipient
     */
    public OutgoingTransfer(String date, double amount, String description, String sender, String recipient)
    {
        super(date, amount, description, sender, recipient);
    }

    /**
     * @param transfer
     */
    public OutgoingTransfer(Transfer transfer){
        super(transfer);
    }

    /**
     * @return negativ amount of the Transfer
     */
    @Override
    public double calculate()
    {
        return -super.calculate();
    }
}
