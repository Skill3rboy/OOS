import bank.IncomingTransfer;
import bank.OutgoingTransfer;

import bank.Transfer;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TransferTest {

    static Transfer transfer;
    static Transfer incomingTransfer;
    static Transfer outgoingTransfer;
    static Transfer transferCopy;
    static Transfer transferCopy2;

    @BeforeAll
    @DisplayName("Setup for Transfer")
    public static void setUp(){
       transfer= new Transfer("26.11.2023",1000,"Transfer Test");
        incomingTransfer = new IncomingTransfer("26.11.2023", 1000, "IncomingTransfer Test", "You", "Me");
        outgoingTransfer = new OutgoingTransfer("26.11.2023", 100, "OutgoingTransfer Test", "Me", "You");
        transferCopy = new OutgoingTransfer(outgoingTransfer);
        transferCopy2 = new IncomingTransfer(incomingTransfer);
    }

    @Test
    public void constructorTest3(){
        assertEquals("26.11.2023", transfer.getDate());
        assertEquals(1000,transfer.getAmount());
        assertEquals("Transfer Test", transfer.getDescription());
    }
    @Test
    public void constructorTest5I(){
        assertEquals("26.11.2023", incomingTransfer.getDate());
        assertEquals(1000,incomingTransfer.getAmount());
        assertEquals("IncomingTransfer Test", incomingTransfer.getDescription());
        assertEquals("You", incomingTransfer.getSender());
        assertEquals("Me", incomingTransfer.getRecipient());
    }

    @Test
    public void constructorTest5(){
        assertEquals("26.11.2023",outgoingTransfer.getDate());
        assertEquals(100,outgoingTransfer.getAmount());
        assertEquals("OutgoingTransfer Test", outgoingTransfer.getDescription());
        assertEquals("Me", outgoingTransfer.getSender());
        assertEquals("You", outgoingTransfer.getRecipient());
    }

    @Test
    public void constructorCopyTest(){
        assertEquals(outgoingTransfer.getDate(),transferCopy.getDate());
        assertEquals(outgoingTransfer.getAmount(),transferCopy.getAmount());
        assertEquals(outgoingTransfer.getDescription(),transferCopy.getDescription());
        assertEquals(outgoingTransfer.getSender(),transferCopy.getSender());
        assertEquals(outgoingTransfer.getRecipient(),transferCopy.getRecipient());
    }
    @Test
    public void constructorCopyTest2(){
        assertEquals(incomingTransfer.getDate(),transferCopy2.getDate());
        assertEquals(incomingTransfer.getAmount(),transferCopy2.getAmount());
        assertEquals(incomingTransfer.getDescription(),transferCopy2.getDescription());
        assertEquals(incomingTransfer.getSender(),transferCopy2.getSender());
        assertEquals(incomingTransfer.getRecipient(),transferCopy2.getRecipient());
    }

    @Test
    public void calculateIncomingTransferTest(){
        assertInstanceOf(IncomingTransfer.class, incomingTransfer);
        assertEquals(incomingTransfer.getAmount(),incomingTransfer.calculate());
    }

    @Test
    public void calculateOutgoingTransferTest(){
        assertInstanceOf(OutgoingTransfer.class,outgoingTransfer);
        assertEquals(-outgoingTransfer.getAmount(),outgoingTransfer.calculate());
    }

    @Test
    public void equalsTrueTest(){
        assertEquals(outgoingTransfer,transferCopy);
    }

    @Test
    public void equalsFalseTest(){
        assertNotEquals(incomingTransfer,transferCopy);
    }

    @Test
    public void toStringTest1(){
        assertEquals("Date: 26.11.2023, Amount: 1000.0, Description: IncomingTransfer Test, Sender: You, Recipient: Me",incomingTransfer.toString());
    }
    @Test
    public void toStringTest2(){
        assertEquals("Date: 26.11.2023, Amount: -100.0, Description: OutgoingTransfer Test, Sender: Me, Recipient: You",outgoingTransfer.toString());
    }


}
