import bank.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!"); // Nette Ausgabe
        String date = "9.10.2023";
        double amount = 1000;
        double amountf = -1000; //Fehler Test
        String description = "Testlauf";
        double incomingInterest = 0.05;
        double outgoingInterest = 0.01;
        double outgoingInterestf = 1.01;//Fehler Test
        double incomingInterestf = -0.05;//Fehler Test
        String sender = "Me";
        String recipient = "Me";
        Transfer transf = new Transfer(date, amount, description, sender, recipient);
        Transfer transfF = new Transfer(date, amountf, description, sender, recipient);//Fehler Test
        Payment paymen = new Payment(date, amount, description, incomingInterest, outgoingInterest);
        Payment paymenF = new Payment(date, amount, description, incomingInterestf, outgoingInterestf);//Fehler Test

        Payment payC = new Payment(paymen); // Copytest
        Transfer TranC = new Transfer(transf); // Copytest
        System.out.println("Transfer: ");
        transf.printObject();
        System.out.println("Payment: ");
        paymen.printObject();
        System.out.println("Payment Copy:");
        payC.printObject();
        System.out.println("Transfer Copy:");
        TranC.printObject();
    }
}