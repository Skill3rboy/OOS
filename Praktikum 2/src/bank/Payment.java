package bank;

/**
 * Payment Class is a subclass from Transaction and uses CalculateBill
 */
public class Payment extends Transaction implements CalculateBill {

    /**
     * Incoming interest from a Transaction
     */
    private double incomingInterest;
    /**
     * Outgoing interest from a Transaction
     */
    private double outgoingInterest;


    /**
     * Standard Transaction-constructor with Payment Attributes
     *
     * @param date
     * @param amount
     * @param description
     * @param incomingInterest
     * @param outgoingInterest
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) {
        super(date, amount, description);
        setOutgoingInterest(outgoingInterest);
        setIncomingInterest(incomingInterest);


    }

    /**
     * Copy Constructor
     *
     * @param payment
     */

    public Payment(Payment payment) {
        super(payment);
        setOutgoingInterest(payment.getOutgoingInterest());
        setIncomingInterest(payment.getIncomingInterest());

    }




    /**
     * @return  IncomingInterest
     */
    public double getIncomingInterest() {
        return this.incomingInterest;
    }

    /** Setter for incomingInterest
     * @param incomingInterest
     */
    public void setIncomingInterest(double incomingInterest) {

        if (!(0 <= incomingInterest && incomingInterest <= 1)) {
            System.out.println("Wrong Input, Incoming-Interest has to be between 0 and 1!");
            return;
        } else {
            this.incomingInterest = incomingInterest;
        }
    }

    /**
     * @return  OutgoingInterest
     */
    public double getOutgoingInterest() {
        return this.outgoingInterest;
    }

    /** Setter for  outgoingInterest
     *
     * @param outgoingInterest
     */
    public void setOutgoingInterest(double outgoingInterest) {
        if (!(0 <= outgoingInterest && outgoingInterest <= 1)) {
            System.out.println("Wrong Input, Outgoing-Interest has to be between 0 and 1!");
            return;
        } else {
            this.outgoingInterest = outgoingInterest;
        }
    }

    /**
     * @return String with the attributes from Payment
     */
    @Override
    public String toString() {
        String ans = super.toString();
        ans += ", IncomingInterest: " + getIncomingInterest() + ", OutgoingInterest: " + getOutgoingInterest();
        return ans;
    }


    /**
     * @return Calculated Amount in a Payment
     */
    @Override
    public double calculate() {
        if (getAmount() > 0) {
            return getAmount() + (getAmount() * getIncomingInterest());
        } else
            return getAmount() + (getAmount() * getOutgoingInterest());
    }

    /**Check if the object is the same
     * @param object which should be checked
     * @return True if equal
     */
    @Override
    public boolean equals(Object object) {
        Payment other = (Payment) object;
        return ((super.equals(object)) && (Double.compare(this.getIncomingInterest(), other.getIncomingInterest()) == 0) && (Double.compare(this.getOutgoingInterest(), other.getOutgoingInterest()) == 0));

    }
}
