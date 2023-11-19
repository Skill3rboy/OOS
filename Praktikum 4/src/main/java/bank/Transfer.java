package bank;

import bank.exceptions.TransferAmountException;

/**
 * Transfer Class is a subclass from Transaction and uses CalculateBill
 */
public class Transfer extends Transaction {


    /**
     * Name off the Sender
     */
    private String sender;
    /**
     * Name off the Recipient
     */
    private String recipient;


    /**
     * Standard Transaction-constructor with Transfer Attributes
     *
     * @param date
     * @param amount
     * @param description
     * @param sender
     * @param recipient
     */

    public Transfer(String date, double amount, String description, String sender, String recipient) {
        super(date, 0, description);
        setAmount(amount);
        setRecipient(recipient);
        setSender(sender);


    }

    /**
     * Copy Constructor
     *
     * @param transfer
     */
    public Transfer(Transfer transfer) {
        super(transfer);
        setRecipient(transfer.getRecipient());
        setSender(transfer.getSender());
    }


    /** Setter for Amount of the Transfer
     * @param amount
     */
    @Override
    public void setAmount(double amount) {
        try {
            if (amount < 0) {
               throw new TransferAmountException();
            } else {
                this.amount = amount;
            }
        } catch (TransferAmountException e) {
            this.amount=0;
        }

    }

    /**
     * @return Sender
     */
    public String getSender() {
        return this.sender;
    }

    /** Setter for the Sender of the Transfer
     * @param sender
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return recipient
     */
    public String getRecipient() {
        return this.recipient;
    }

    /**Setter for the Recipient of the Transfer
     * @param recipient
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * @return String with the attributes from Transfers
     */
    @Override
    public String toString() {
        String ans = super.toString();
        ans += ", Sender: " + getSender() + ", Recipient: " + getRecipient();
        return ans;
    }

    /**
     * @return Calculated Amount of the Transfer
     */
    @Override
    public double calculate() {
        return getAmount();
    }

    /**Check if the object is the same
     * @param object which should be checked
     * @return True if equal
     */
    @Override
    public boolean equals(Object object) {
        Transfer other = (Transfer) object;
        return ((super.equals(object)) && (this.getRecipient().equals(other.getRecipient())) && (this.getSender().equals(other.getSender())));
    }
}


