import bank.*;


/**
 * Dies ist ein Javadoc-Kommentar.
 *
 * @author Fabian Jülich
 * @version 1.0
 */
public class Main {
    /**
     * Main Methode in der Die Verschiedenen Aufgaben getestet werden
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Hello World!"); // Nette Ausgabe
        String date = "9.10.2023";
        double amount = 1000;
        double amount2 = -1000;
        String description = "Testlauf";
        double incomingInterest = 0.05;
        double outgoingInterest = 0.01;
        String sender = "Me";
        String recipient = "Me";
        Transfer transf = new Transfer(date, amount, description, sender, recipient);
        Payment paymen = new Payment(date, amount, description, incomingInterest, outgoingInterest);
        Payment paymenAus = new Payment(date, amount2, description, incomingInterest, outgoingInterest);


        System.out.println("Transfer: " + transf.toString());

        System.out.println("Payment: " + paymen.toString());
        System.out.println("PaymentAus: " + paymenAus.toString());

        Payment transaction1 = new Payment("1.1.11", 100, "Test", 0.1, 0.2);
        Payment transaction2 = new Payment("1.1.11", 100, "Test", 0.1, 0.2);
        System.out.println(transaction1.equals(transaction2));

    }
}