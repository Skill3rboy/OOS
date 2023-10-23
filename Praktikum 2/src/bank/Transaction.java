package bank;

public abstract class Transaction implements CalculateBill {

    /**
     * Datum im Format DD.MM.YYYY
     */
    protected String date;
    /**
     * Geldmenge der Überweisung
     */
    protected double amount;
    /**
     * Beschreibung des Vorgangs
     */
    protected String description;

    /**
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
     * copy Konstructor
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

    /**
     * @param dateN setter
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

    /**
     * @param amountN setter
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

    /**
     * @param descriptionN setter
     */
    public void setDescription(String descriptionN) {
        this.description = descriptionN;
    }

    /**
     * @return Attribute der Transaction
     */
    @Override
    public String toString() {
        return "Date: " + getDate() + ", amount: " + calculate() + ",  Description: " + getDescription();
    }

    /**
     * @param object Welches Verglichen wird
     * @return boolean, True wenn es gleich its
     */
    @Override
    public boolean equals(Object object) {
        Transaction other = (Transaction) object;
        return (this.getDate().equals(other.getDate())) && ((Double.compare(this.getAmount(), other.getAmount())) == 0) && (this.getDescription().equals(other.getDescription()));
    }
}
