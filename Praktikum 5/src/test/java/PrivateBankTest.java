import bank.*;
import bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrivateBankTest {

    static PrivateBank privateBank;
    static PrivateBank copyPrivateBank;
    static String directory = "./test_data";

    @DisplayName("Setup for PrivateBank")
    @BeforeAll
    public static void setUp() throws IOException, AccountAlreadyExistsException, TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IncomingException, OutgoingException {
        File folder = new File(directory);
        if (folder.exists()){
            File[] listOfFiles = folder.listFiles();
            if(listOfFiles != null){
                for (File file:listOfFiles){
                    file.delete();
                }
                Files.deleteIfExists(Path.of(directory));
            }
        }
        privateBank = new PrivateBank("JUnit Bank",0.,0.1,"junit5");

        privateBank.createAccount("Adam");
        privateBank.createAccount("Eva");
        privateBank.addTransaction("Adam", new Payment("19.01.2011", -500, "Payment", 0.9, 0.25));
        privateBank.addTransaction("Eva", new IncomingTransfer("03.03.2000", 500, "IncomingTransfer from Adam to Eva", "Adam", "Eva"));
        copyPrivateBank = new PrivateBank(privateBank);
    }

    @DisplayName("Testing Constructor")
    @Test
    @Order(0)
    public void constructorTest(){
        assertAll("PrivateBank",
                ()->assertEquals("JUnit Bank", privateBank.getName()),
                () -> assertEquals("junit5", privateBank.getDirectoryName()),
                () -> assertEquals(0, privateBank.getIncomingInterest()),
                () -> assertEquals(0.1, privateBank.getOutgoingInterest()));
    }

    @DisplayName("Testing Copy-Constructor")
    @Test
    @Order(1)
    public void constructorCopyTest(){
        assertAll("CopyBank",
                ()->assertEquals(privateBank.getName(), copyPrivateBank.getName()),
                () -> assertEquals(privateBank.getDirectoryName(), copyPrivateBank.getDirectoryName()),
                () -> assertEquals(privateBank.getIncomingInterest(), copyPrivateBank.getIncomingInterest()),
                () -> assertEquals(privateBank.getOutgoingInterest(), copyPrivateBank.getOutgoingInterest()));
    }

    @DisplayName("Duplicate account")
    @ParameterizedTest
    @Order(2)
    @ValueSource(strings={"Adam", "Eva"})
    public void createDuplicateAccountTest(String account){
        Exception exception = assertThrows(AccountAlreadyExistsException.class,
                ()-> privateBank.createAccount(account));
        System.out.println(exception.getMessage());
    }

    @DisplayName("Valid Account")
    @ParameterizedTest
    @Order(3)
    @ValueSource(strings = {"Kain", "Abel", "Set"})
    public void createValidAccountTest(String account){
        assertDoesNotThrow(
                ()->privateBank.createAccount(account)
        );
    }

    @DisplayName("Valid Account with Transactions")
    @ParameterizedTest
    @Order(4)
    @ValueSource(strings = {"Ismael", "Isaak"})
    public void createValidAccountTransactionsTest(String account){
        assertDoesNotThrow(
                ()->privateBank.createAccount(account, List.of(
                        new Payment("26.11.2023", -1000, "Payment 2", 0.5, 0.7),
                        new OutgoingTransfer("26.11.2023", 1000, "OutgoingTransfer to Adam", account, "Adam")
                ))
        );
    }

    @DisplayName("Duplicate Account with Transactions")
    @ParameterizedTest
    @Order(5)
    @ValueSource(strings = {"Adam", "Eva", "Kain", "Abel", "Set","Ismael","Isaak"})
    public void createDuplicateAccountTransactionsTest(String account){
        Exception exception = assertThrows(AccountAlreadyExistsException.class,
                ()-> privateBank.createAccount(account, List.of(
                     new Payment("26.11.2023", -1000,"Payment 2", 0.5,0.7)
                ))
                );
        System.out.println(exception.getMessage());
    }

    @DisplayName("Valid Transaction to Valid Account")
    @ParameterizedTest
    @Order(6)
    @ValueSource(strings = {"Adam","Kain","Abel","Set"})
    public void addValidTransactionToValidAccountTest(String account){
        assertDoesNotThrow(
                ()->privateBank.addTransaction(account,
                        new IncomingTransfer("26.11.2023", 1000,"OutgoingTransfer to Adam", "Eva",account))
        );
    }
    @DisplayName("Duplicate Transaction to Valid Account")
    @ParameterizedTest
    @Order(7)
    @ValueSource(strings = {"Ismael", "Isaak"})
    public void addDuplicateTransactionToValidAccountTest(String account){
        Exception exception = assertThrows(TransactionAlreadyExistException.class,
                ()-> privateBank.addTransaction(account,
                        new Payment("26.11.2023", -1000,"Payment 2", 0.5,0.7))
        );
        System.out.println(exception.getMessage());
    }

    @DisplayName("Valid Transaction to Invalid Account")
    @ParameterizedTest
    @Order(8)
    @ValueSource(strings = {"Maria","Josef","Moses"})
    public void addTransactionToInvalidAccountTest(String account){
        Exception exception = assertThrows(AccountDoesNotExistException.class,
                ()->privateBank.addTransaction(account,
                        new Payment("19.01.2011", -500, "Payment", 0.9, 0.25))
                );
        System.out.println(exception.getMessage());
    }

    @DisplayName("Remove Valid Transaction")
    @Test
    @Order(9)
    public void removeValidTransactionTest(){
        assertDoesNotThrow(
                ()->privateBank.createAccount("account"));
        Transaction transaction = new Payment("26.11.2023",-1000,"Payment 2", 0.5,0.7);
        assertDoesNotThrow(
                ()->privateBank.addTransaction("account",transaction));
        assertTrue(privateBank.containsTransaction("account",transaction));
        assertDoesNotThrow(
                ()->privateBank.removeTransaction("account",transaction));
    }

    @DisplayName("Remove Invalid Transcation")
    @ParameterizedTest
    @Order(10)
    @ValueSource(strings = {"Ismael","Isaak"})
    public void removeInvalidTransactionTest(String account){
        Exception exception = assertThrows(TransactionDoesNotExistException.class,
                ()-> privateBank.removeTransaction(account,
                        new Payment("19.01.2011", -500, "Payment", 0.9, 0.25)));
        System.out.println(exception.getMessage());
    }

    @DisplayName("ContainsTransaction True")
    @ParameterizedTest
    @Order(11)
    @ValueSource(strings = {"Ismael","Isaak"})
    public void containsTransactionTrueTest(String account){
        assertTrue(privateBank.containsTransaction(account,
                new OutgoingTransfer("26.11.2023", 1000,"OutgoingTransfer to Adam",account,"Adam" )));
    }

    @DisplayName("ContainsTransaction False")
    @ParameterizedTest
    @Order(12)
    @ValueSource(strings = {"Adam", "Kain","Abel","Set","Eva"})
    public void containsTransactionFalseTest(String account){
        assertFalse(privateBank.containsTransaction(account,
                new OutgoingTransfer("26.11.2023", 1000,"OutgoingTransfer to Adam",account,"Adam")));
    }

    @DisplayName("Account Balance")
    @Test
    @Order(13)
    public void getAccountBalanceTest(){
        assertDoesNotThrow(()->privateBank.createAccount("payment"));
        assertEquals(0.,privateBank.getAccountBalance("payment"));
        Transaction transaction1 = new Payment("date",1000,"pay1",0.5,0.5);
        Transaction transaction2 = new Payment("date2",500,"pay2",0.5,0.5);
        assertDoesNotThrow(()->privateBank.addTransaction("payment",transaction1));
        assertEquals(1000.0,privateBank.getAccountBalance("payment"));
        assertDoesNotThrow(()->privateBank.addTransaction("payment",transaction2));
        assertEquals(1500.0,privateBank.getAccountBalance("payment"));
    }

    @DisplayName("TransactionList")
    @Test
    @Order(14)
    public void getTransactionListTest(){
        List<Transaction> transactionList= List.of(
            new Payment("19.01.2011", -500, "Payment", 0.0, 0.1),
            new IncomingTransfer("26.11.2023",1000,"OutgoingTransfer to Adam", "Eva", "Adam"));
        assertEquals(transactionList,privateBank.getTransactions("Adam"));
    }

    @DisplayName("TransactionList Type")
    @Test
    @Order(15)
    public void getTransactionListTypeTest(){
        List<Transaction> transactionList= List.of(
                new OutgoingTransfer("26.11.2023",1000,"OutgoingTransfer to Adam", "Eva","Adam"));
        assertDoesNotThrow(()->privateBank.addTransaction("Eva",
                new OutgoingTransfer("26.11.2023",1000,"OutgoingTransfer to Adam", "Eva","Adam") ));
        assertEquals(transactionList,privateBank.getTransactionsByType("Eva",false));
    }

    @DisplayName("TransactionList Sort")
    @Test
    @Order(16)
    public void getTransactionListSortTest(){
        assertDoesNotThrow(()-> privateBank.removeTransaction("Eva",
                new OutgoingTransfer("26.11.2023",1000,"OutgoingTransfer to Adam", "Eva","Adam")));
        assertEquals(List.of(
                new IncomingTransfer("03.03.2000", 500, "IncomingTransfer from Adam to Eva", "Adam", "Eva")),
                privateBank.getTransactionsSorted("Eva", true));
    }

    @DisplayName("ToString")
    @Test
    @Order(17)
    public void toStringTest(){
        assertEquals("Name: JUnit Bank\n" +
                " IncomingInterest: 0.0\n" +
                " OutgoingInterest: 0.1\n" +
                " Adam => \n" +
                "\t\tDate: 19.01.2011, Amount: -550.0, Description: Payment, IncomingInterest: 0.0, OutgoingInterest: 0.1\t\tDate: 26.11.2023, Amount: 1000.0, Description: OutgoingTransfer to Adam, Sender: Eva, Recipient: AdamEva => \n" +
                "\t\tDate: 03.03.2000, Amount: 500.0, Description: IncomingTransfer from Adam to Eva, Sender: Adam, Recipient: EvaSet => \n" +
                "\t\tDate: 26.11.2023, Amount: 1000.0, Description: OutgoingTransfer to Adam, Sender: Eva, Recipient: SetAbel => \n" +
                "\t\tDate: 26.11.2023, Amount: 1000.0, Description: OutgoingTransfer to Adam, Sender: Eva, Recipient: AbelKain => \n" +
                "\t\tDate: 26.11.2023, Amount: 1000.0, Description: OutgoingTransfer to Adam, Sender: Eva, Recipient: Kainpayment => \n" +
                "\t\tDate: date, Amount: 1000.0, Description: pay1, IncomingInterest: 0.0, OutgoingInterest: 0.1\t\tDate: date2, Amount: 500.0, Description: pay2, IncomingInterest: 0.0, OutgoingInterest: 0.1Isaak => \n" +
                "\t\tDate: 26.11.2023, Amount: -1700.0, Description: Payment 2, IncomingInterest: 0.5, OutgoingInterest: 0.7\t\tDate: 26.11.2023, Amount: -1000.0, Description: OutgoingTransfer to Adam, Sender: Isaak, Recipient: AdamIsmael => \n" +
                "\t\tDate: 26.11.2023, Amount: -1700.0, Description: Payment 2, IncomingInterest: 0.5, OutgoingInterest: 0.7\t\tDate: 26.11.2023, Amount: -1000.0, Description: OutgoingTransfer to Adam, Sender: Ismael, Recipient: Adamaccount => \n",privateBank.toString());
    }

    @DisplayName("Equals")
    @Test
    @Order(18)
    public void equalsTest(){
        assertTrue(privateBank.equals(copyPrivateBank));
    }

    @DisplayName("Equals False")
    @Test
    @Order(19)
    public void equalsFalseTest(){
        assertFalse(privateBank.equals(new PrivateBank("Falsch",0,0,"data")));
    }
}
