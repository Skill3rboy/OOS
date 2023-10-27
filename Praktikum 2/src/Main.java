import bank.*;

import java.sql.SQLOutput;


/**
 *
 *
 * @author Fabian Jülich
 * @version 1.0
 */
public class Main {

    /**
     * Main Methode
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("\n Hello World! \n "); // Love it

        paymentTest();
        transferTest();

    }

    public static void paymentTest()
    {
        System.out.println("Starting the Payment-Class test! \n \n" );


        String date = "9.10.2023";
        double amountIn = 1000;
        double amountOut = -1000;
        String description = "Test";
        double incomingInterest = 0.05;
        double outgoingInterest = 0.01;

        Payment payment = new Payment(date,amountIn, description, incomingInterest, outgoingInterest);
        Payment paymentIn = new Payment(date, amountIn, description, incomingInterest, outgoingInterest);
        Payment paymentOut = new Payment(date, amountOut, description, incomingInterest, outgoingInterest);

        System.out.println("PaymentIn Calculate: " + paymentIn.calculate());
        System.out.println("PaymentOut Calculate: " + paymentOut.calculate());
        System.out.println(payment.toString());
        payment.setIncomingInterest(2); // Error
        payment.setOutgoingInterest(2); // Error
        System.out.println(payment.toString());

        Payment paymentC= new Payment(payment);
        System.out.println(paymentC.toString());

        System.out.println("Payment equals PaymentC? "+payment.equals(paymentC));

        paymentC.setIncomingInterest(0.5);
        System.out.println(paymentC.toString());
        System.out.println("Payment equals PaymentC? "+payment.equals(paymentC));

        System.out.println("Test Over! \n \n" );
    }
    public static void transferTest()
    {
        System.out.println("Starting the Transfer-Class test! \n \n" );

        String date = "9.10.2023";
        double amountIn = 1000;
        double amountOut = -1000;
        String description = "Test";
        String sender = "Me";
        String recipient = "You";

        Transfer transfer = new Transfer(date,amountIn, description, sender, recipient);

        System.out.println("Transfer Calculate: " + transfer.calculate());

        System.out.println(transfer.toString());
        transfer.setAmount(-5); // Error
        System.out.println(transfer.toString());

        Transfer transferC= new Transfer(transfer);
        System.out.println(transferC.toString());

        System.out.println("Transfer equals TransferC? "+transfer.equals(transferC));

        transferC.setAmount(50);
        System.out.println(transferC.toString());
        System.out.println("Transfer equals TransferC? "+transfer.equals(transferC));

        System.out.println("Test Over! \n \n " );
    }

}

