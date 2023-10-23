package bank;

public class Transaction {

    protected String date;//Datum im Format DD.MM.YYYY
    protected double amount; // Geldmenge der Überweisung
    protected String description; // Beschreibung des Vorgangs

    public String getDate() { //getter Date
        return this.date;
    }

    public void setDate(String dateN) // setter Date
    {
        this.date = dateN;
    }

    public double getAmount() { // getter Amount
        return this.amount;
    }

    public void setAmount(double amountN) { //setter Amount
        this.amount = amountN;
    }

    public String getDescription() { //getter Description
        return this.description;
    }

    public void setDescription(String descriptionN) { // setter Description
        this.description = descriptionN;
    }

    public void printObject() { // Print methode die Auf der Konsole Alle Atrribute Ausgibt
        System.out.println("Date: " + getDate());
        System.out.println("Amount: " + getAmount());
        System.out.println("Description: " + getDescription());

    }

}
