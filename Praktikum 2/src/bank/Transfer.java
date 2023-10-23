package bank;

public class Transfer extends Transaction implements CalculateBill {


    /**
     * Name des Senders
     */
    private String sender;
    /**
     * Name des Empfängers
     */
    private String recipient;


    /**
     * Konstrukor mit den Transfer Attributen
     *
     * @param dateN
     * @param amountN
     * @param descriptionN
     * @param senderN
     * @param recipientN
     */

    public Transfer(String dateN, double amountN, String descriptionN, String senderN, String recipientN) {
        super(dateN, 0, descriptionN);
        setAmount(amountN);
        setRecipient(recipientN);
        setSender(senderN);


    }

    /**
     * Copy Konstruktor
     *
     * @param transfer
     */
    public Transfer(Transfer transfer) {
        super(transfer);
        setRecipient(transfer.getRecipient());
        setSender(transfer.getSender());
    }


    /**
     * @param amountN setter
     */
    @Override
    public void setAmount(double amountN) {
        if (amountN < 0) {
            System.out.println("Fehlerhafte Eingabe, Amount muss positiv sein");
            return;
        } else {
            this.amount = amountN;
        }
    }

    /**
     * @return Sender
     */
    public String getSender() {
        return this.sender;
    }

    /**
     * @param senderN setter
     */
    public void setSender(String senderN) {
        this.sender = senderN;
    }

    /**
     * @return recipient
     */
    public String getRecipient() {
        return this.recipient;
    }

    /**
     * @param recipientN setter
     */
    public void setRecipient(String recipientN) {
        this.recipient = recipientN;
    }

    /**
     * @return Attribute des Transfers
     */
    @Override
    public String toString() {
        String ans = super.toString();
        ans += ", Sender: " + getSender() + ", Recipient: " + getRecipient();
        return ans;
    }

    /**
     * @return Berechneter Amount
     */
    @Override
    public double calculate() {
        return getAmount();
    }

    /**
     * @param object Welches Verglichen wird
     * @return boolean, True wenn es gleich its
     */
    @Override
    public boolean equals(Object object) {
        Transfer other = (Transfer) object;
        return ((super.equals(object)) && (this.getRecipient().equals(other.getRecipient())) && (this.getSender().equals(other.getSender())));
    }
}


