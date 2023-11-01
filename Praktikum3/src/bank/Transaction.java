package bank;

/**
 * Main Transaction-class
 */
public abstract class Transaction implements CalculateBill {

    /**
     * Date in Format DD.MM.YYYY
     */
    protected String date;
    /**
     * Amount of Money
     */
    protected double amount;
    /**
     * Description of the Action
     */
    protected String description;

    /**Normal Constructor
     * @param date
     * @param amount
     * @param description
     */
    public Transaction(String date, double amount, String description) {
        setDate(date);
        setAmount(amount);
        setDescription(description);
    }

    /**
     * copy Constructor
     *
     * @param transaction
     */

    public Transaction(Transaction transaction) {
        setDate(transaction.getDate());
        setAmount(transaction.getAmount());
        setDescription(transaction.getDescription());
    }

    /**
     * @return Date
     */
    public String getDate() {
        return this.date;
    }

    /**Setter for Date
     * @param dateN
     */
    public void setDate(String dateN) {
        this.date = dateN;
    }

    /**
     * @return amount
     */
    public double getAmount() {
        return this.amount;
    }

    /** Setter for Amount
     * @param amountN
     */
    public void setAmount(double amountN) {
        this.amount = amountN;
    }

    /**
     * @return Description
     */
    public String getDescription() {
        return this.description;
    }

    /**Setter for Description
     * @param descriptionN
     */
    public void setDescription(String descriptionN) {
        this.description = descriptionN;
    }

    /**
     * @return Attributes from Transaction
     */
    @Override
    public String toString() {
        return "Date: " + getDate() + ", Amount: " + calculate() + ",  Description: " + getDescription();
    }

    /** Check if the object is the same
     * @param object
     * @return True if equal
     */
    @Override
    public boolean equals(Object object) {
        Transaction other = (Transaction) object;
        return (this.getDate().equals(other.getDate())) && ((Double.compare(this.getAmount(), other.getAmount())) == 0) && (this.getDescription().equals(other.getDescription()));
    }

    public boolean validateTransactionAttributes(){
        return this.getDate() != null && !this.getDate().isEmpty() && this.getDescription() != null && this.getAmount() != 0.;
    }
}
