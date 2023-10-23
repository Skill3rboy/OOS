package bank;

public class Transfer extends Transaction {

    private String sender; // Name des Senders
    private String recipient; // Name des Empfängers

    public Transfer(String dateN, double amountN, String descriptionN) { // Konstruktor mit den Basis Attributen
        setDate(dateN);
        setAmount(amountN);
        setDescription(descriptionN);

    }

    //Konstrukor mit den Transfer Attributen
    public Transfer(String dateN, double amountN, String descriptionN, String senderN, String recipientN) {
        this(dateN, amountN, descriptionN);
        setRecipient(recipientN);
        setSender(senderN);

    }


    public Transfer(Transfer tranf) {//Copy Konstruktor
        this(tranf.getDate(), tranf.getAmount(), tranf.getDescription());
        setRecipient(tranf.getRecipient());
        setSender(tranf.getSender());
    }

    @Override
    public void setAmount(double amountN) {
        if (amountN < 0) {
            System.out.println("Fehlerhafte Eingabe, Amount muss positiv sein");
            return;
        } else {
            this.amount = amountN;
        }
    }

    public String getSender() { //getter sender
        return this.sender;
    }

    public void setSender(String senderN) {// setter Sender
        this.sender = senderN;
    }

    public String getRecipient() { //getter Recipient
        return this.recipient;
    }

    public void setRecipient(String recipientN) { // Setter Recipient
        this.recipient = recipientN;
    }

    @Override
    public void printObject() { // Schreibt alle Attribute auf die Konsole
        super.printObject();
        System.out.println("Sender: " + getSender());
        System.out.println("Recipient: " + getRecipient());
    }


}
