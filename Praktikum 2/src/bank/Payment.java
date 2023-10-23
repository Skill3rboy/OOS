package bank;

public class Payment extends Transaction implements CalculateBill {

    /**
     * Attirbut Zinsen bei einer Einzahlung
     */
    private double incomingInterest;
    /**
     * Attirbut Zinsen bei einer Auszahlung
     */
    private double outgoingInterest;


    /**
     * Konstruktor mit den Zusätzlichen Payment Attributen
     *
     * @param dateN
     * @param amountN
     * @param descriptionN
     * @param incomingInterestN
     * @param outgoingInterestN
     */
    public Payment(String dateN, double amountN, String descriptionN, double incomingInterestN, double outgoingInterestN) {
        super(dateN, amountN, descriptionN);
        setOutgoingInterest(outgoingInterestN);
        setIncomingInterest(incomingInterestN);


    }

    /**
     * Copy Konstruktor
     *
     * @param paymenN
     */

    public Payment(Payment paymenN) {
        super(paymenN);
        setOutgoingInterest(paymenN.getOutgoingInterest());
        setIncomingInterest(paymenN.getIncomingInterest());

    }


    /**
     * @return double IncomingInterest
     */
    public double getIncomingInterest() {
        return this.incomingInterest;
    }

    /**
     * @param incomingInterestN Setter
     */
    public void setIncomingInterest(double incomingInterestN) {

        if (!(0 <= incomingInterestN && incomingInterestN <= 1)) {
            System.out.println("Fehlerhafte Eingabe, Interest muss zwischen 0 und 1 sein");
            return;
        } else {
            this.incomingInterest = incomingInterestN;
        }
    }

    /**
     * @return double OutgoingInterest
     */
    public double getOutgoingInterest() {
        return this.outgoingInterest;
    }

    /**
     * @param outgoingInterestN setter
     */
    public void setOutgoingInterest(double outgoingInterestN) {
        if (!(0 <= outgoingInterestN && outgoingInterestN <= 1)) {
            System.out.println("Fehlerhafte Eingabe, Interest muss zwischen 0 und 1 sein");
            return;
        } else {
            this.outgoingInterest = outgoingInterestN;
        }
    }

    /**
     * @return Attribute als String
     */
    @Override
    public String toString() {
        String ans = super.toString();
        ans += ", IncomingInterest: " + getIncomingInterest() + ", OutgoingInterest: " + getOutgoingInterest();
        return ans;
    }


    /**
     * @return double Berechneter Amount
     */
    @Override
    public double calculate() {
        if (getAmount() > 0) {
            return getAmount() + (getAmount() * getIncomingInterest());
        } else
            return getAmount() + (getAmount() * getOutgoingInterest());
    }

    /**
     * @param object Welches Verglichen wird
     * @return boolean, True wenn es gleich its
     */
    @Override
    public boolean equals(Object object) {
        Payment other = (Payment) object;
        return ((super.equals(object)) && (Double.compare(this.getIncomingInterest(), other.getIncomingInterest()) == 0) && (Double.compare(this.getOutgoingInterest(), other.getOutgoingInterest()) == 0));

    }
}
