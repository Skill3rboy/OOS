package bank;

import bank.exceptions.*;

/**
 * Payment Class is a subclass from Transaction and uses CalculateBill
 */
public class Payment extends Transaction  {

    /**
     * Incoming interest from a Transaction
     */
    private double incomingInterest;
    /**
     * Outgoing interest from a Transaction
     */
    private double outgoingInterest;

    /**Standard Constructor without Payment Attributes
     *
     * @param date
     * @param amount
     * @param description
     */
    public Payment(String date, double amount, String description){
        super(date,amount,description);
    }
    /**
     * Standard Transaction-constructor with Payment Attributes
     *
     * @param date
     * @param amount
     * @param description
     * @param incomingInterest
     * @param outgoingInterest
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest)  {
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
    public void setIncomingInterest(double incomingInterest)  {
        try {
            if (!(0 <= incomingInterest && incomingInterest <= 1)) {
                throw new IncomingException();
            } else {
                this.incomingInterest = incomingInterest;
            }
        }catch (IncomingException e)
        {
            this.incomingInterest=0;
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
    public void setOutgoingInterest(double outgoingInterest){
        try {
            if (!(0 <= outgoingInterest && outgoingInterest <= 1)) {
                throw new OutgoingException();
            } else {
                this.outgoingInterest = outgoingInterest;
            }
        }catch (OutgoingException e)
        {
            this.outgoingInterest=0;
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
            return getAmount() - (getAmount() * getIncomingInterest());
        } else
            return getAmount() + (getAmount() * getOutgoingInterest());
    }

    /**Check if the object is the same
     * @param object which should be checked
     * @return True if equal
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) return false;
        Payment other = (Payment) object;
        return ((super.equals(object)) && (Double.compare(this.getIncomingInterest(), other.getIncomingInterest()) == 0) && (Double.compare(this.getOutgoingInterest(), other.getOutgoingInterest()) == 0));

    }
}
