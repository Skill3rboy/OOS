import  bank.Payment;
import  org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    static Payment payment1 ;
    static Payment payment2;
    static Payment paymentCopy;

    @BeforeAll
    @DisplayName("Setup for Payment")
    public static void setUp(){
    payment1 = new Payment("26.11.2023",1000,"Payment 1");
    payment2=new Payment("26.11.2023", -1000, "Payment 2", 0.5,0.7);
    paymentCopy= new Payment(payment2);
    }

    @Test
    public void constructorTest3(){
        assertEquals("26.11.2023",payment1.getDate());
        assertEquals(1000,payment1.getAmount());
        assertEquals("Payment 1", payment1.getDescription());
    }
    @Test
    public void constructorTest5(){
        assertEquals("26.11.2023",payment2.getDate());
        assertEquals(-1000,payment2.getAmount());
        assertEquals("Payment 2", payment2.getDescription());
        assertEquals(0.5,payment2.getIncomingInterest());
        assertEquals(0.7,payment2.getOutgoingInterest());
    }

    @Test
    public void constructorTestCopy(){
        assertEquals(payment2.getDate(),paymentCopy.getDate());
        assertEquals(payment2.getAmount(),paymentCopy.getAmount());
        assertEquals(payment2.getDescription(), paymentCopy.getDescription());
        assertEquals(payment2.getIncomingInterest(),paymentCopy.getIncomingInterest());
        assertEquals(payment2.getOutgoingInterest(),paymentCopy.getOutgoingInterest());
    }


    @Test
    public void calculateIncomingTest(){
        double expected = payment1.getAmount() - (payment1.getIncomingInterest()*payment1.getAmount());
        assertTrue(payment1.getAmount()>=0);
        assertEquals(expected,payment1.calculate());
    }

    @Test
    public void calculateOutgoingTest(){
        double expected = payment2.getAmount() + (payment2.getOutgoingInterest() * payment2.getAmount());
        assertTrue(payment2.getAmount() < 0);
        assertEquals(expected, payment2.calculate());
    }

    @Test
    public void equalsTrueTest(){
        assertEquals(payment2,paymentCopy);
    }

    @Test
    public void equalsFalseTest(){
        assertNotEquals(payment1,paymentCopy);
    }

    @Test
    public void toStringTest(){
        String text = "Date: 26.11.2023, Amount: -1700.0, Description: Payment 2, IncomingInterest: 0.5, OutgoingInterest: 0.7";
        assertEquals(text,payment2.toString());
    }
}
