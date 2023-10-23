package bank;

public class Payment extends Transaction {


    private double incomingInterest; // Zinsen bei einer Einzahlung
    private double outgoingInterest; // Zinsen bei einer Auszahlung

    public Payment(String dateN, double amountN, String descriptionN) { //Konstruktor mit Standart Argumenten
        setDate(dateN);
        setAmount(amountN);
        setDescription(descriptionN);
    }


    // Konstruktor mit den Zusätzlichen Payment Attributen
    public Payment(String dateN, double amountN, String descriptionN, double incomingInterestN, double outgoingInterestN) {
        this(dateN, amountN, descriptionN);

        setIncomingInterest(incomingInterestN);
        setOutgoingInterest(outgoingInterestN);


    }

    //Copy Konstruktor
    public Payment(Payment paymenN) {
        this(paymenN.getDate(), paymenN.getAmount(), paymenN.getDescription());
        setIncomingInterest(paymenN.getIncomingInterest());
        setOutgoingInterest(paymenN.getOutgoingInterest());
    }


    public double getIncomingInterest() { // getter IncomingInterest
        return this.incomingInterest;
    }

    public void setIncomingInterest(double incomingInterestN) { //setter IncomingInterest

        if (!((0 <= incomingInterestN && incomingInterestN <= 1))) {
            System.out.println("Fehlerhafte Eingabe, Interest muss zwischen 0 und 1 sein");
            return;
        } else {
            this.incomingInterest = incomingInterestN;
        }
    }

    public double getOutgoingInterest() { // getter OutgoingInterest
        return this.outgoingInterest;
    }

    public void setOutgoingInterest(double outgoingInterestN) { // setter OutgoingInterest
        if (!((0 <= outgoingInterestN && outgoingInterestN <= 1))) {
            System.out.println("Fehlerhafte Eingabe, Interest muss zwischen 0 und 1 sein");
            return;
        } else {
            this.outgoingInterest = outgoingInterestN;
        }

    }

    @Override
    public void printObject() { // Print methode die Auf der Konsole Alle Atrribute Ausgibt
        super.printObject();
        System.out.println("IncomingInterest: " + getIncomingInterest());
        System.out.println("OutgoingInterest: " + getOutgoingInterest());
    }


}
