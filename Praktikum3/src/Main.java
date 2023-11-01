import bank.*;
import bank.exceptions.*;


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
    public static void main(String[] args)
            throws TransactionAlreadyExistException, AccountAlreadyExistsException, AccountDoesNotExistException, TransactionDoesNotExistException, TransactionAttributeException, IncomingException, OutgoingException {
        System.out.println("\n Hello World! \n "); // Love it

        paymentTest();
        transferTest();
        privateBankTest();

    }

    /**
     * Test Method for Payment Class
     */
    public static void paymentTest (){
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

    /**
     * Test method for TransferClass
     */
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

    /**Test method for Private Bank Class
     * @throws AccountAlreadyExistsException
     * @throws TransactionAlreadyExistException
     * @throws AccountDoesNotExistException
     * @throws TransactionAttributeException
     * @throws IncomingException
     * @throws OutgoingException
     * @throws TransactionDoesNotExistException
     */
    public static void privateBankTest()
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IncomingException, OutgoingException, TransactionDoesNotExistException {

        System.out.println("Starting the Private-Bank-Class test! \n \n" );
        double incomingInterest=0.5;
        double outgoingInterest=0.5;
        String date = "9.10.2023";
        double amountIn = 1000;
        double amountOut = -1000;
        String description = "Test";
        Transaction transaction= new Payment(date,amountIn,description,incomingInterest,outgoingInterest);
        Transaction transaction2= new Payment(date,amountIn,"description",incomingInterest,outgoingInterest);
        PrivateBank privateBank = new PrivateBank("PrivateBank",incomingInterest,outgoingInterest);
        PrivateBank privateBank2 = new PrivateBank("PrivateBank2",incomingInterest,outgoingInterest);
        PrivateBank privateBankC = new PrivateBank(privateBank);
        privateBank.createAccount("Fabian");
        //privateBank.createAccount("Fabian"); // Error
        privateBank.addTransaction("Fabian", transaction);
        //privateBank.addTransaction("Fabian", transaction); // Error
        System.out.println(privateBank.toString()); // 1 Transaction
        privateBank.removeTransaction("Fabian",transaction);
        System.out.println(privateBank.toString()); // 0 Transaction
        System.out.println(privateBank.containsTransaction("Fabian",transaction)); // False
        privateBank.addTransaction("Fabian", transaction);
        System.out.println(privateBank.containsTransaction("Fabian",transaction)); // True
        System.out.println(privateBank.getAccountBalance("Fabian")); // 1500
        privateBank.addTransaction("Fabian", transaction2);
        System.out.println(privateBank.getAccountBalance("Fabian")); // 3000
        System.out.println(privateBank.toString());

        System.out.println(privateBank.equals(privateBank2)); // false
        System.out.println(privateBank.equals(privateBankC)); //true
        System.out.println("Test Over! \n \n " );
    }

}

