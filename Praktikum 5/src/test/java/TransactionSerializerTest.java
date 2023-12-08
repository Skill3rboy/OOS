import com.google.gson.*;
import bank.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionSerializerTest {
    static String directory = "./test_data";

    @BeforeEach
    public void startUp() {
        File Filedirectory = new File(directory);
        if (!(Filedirectory.exists())) {
            Filedirectory.mkdir();
        }
    }

    @AfterEach
    public void cleanUp() {
        File Filedirectory = new File(directory);
        if (Filedirectory.exists()) {
            File[] files = Filedirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
            Filedirectory.delete();
        }
    }

    @Test
    public void paymentSerializationTest() {
        PrivateBank privateBank = new PrivateBank("Private bank", 0.5, 0.5, directory);

        assertDoesNotThrow(() -> privateBank.createAccount("payment"));
        Transaction transaction = new Payment("26.11.2023", 1000, "Payment", 0.5, 0.5);
        assertDoesNotThrow(() -> privateBank.addTransaction("payment", transaction));
        List<Transaction> transactionList = privateBank.getAccountsToTransactions().get("payment");
        assertEquals(1, transactionList.size());
        Transaction transaction1 = transactionList.get(0);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Payment.class, new TransactionSerializer());
        Gson gson = gsonBuilder.create();

        String json = gson.toJson(transaction1);
        String comparison = "{" +
                "\"CLASSNAME\":\"Payment\"," +
                "\"INSTANCE\":{" +
                "\"incomingInterest\":0.5," +
                "\"outgoingInterest\":0.5," +
                "\"date\":\"26.11.2023\"," +
                "\"amount\":1000.0," +
                "\"description\":\"Payment\"" +
                "}" + "}";
        assertEquals(comparison, json);
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        JsonElement classNameElement = jsonObject.get("CLASSNAME");
        assertNotNull(classNameElement, "CLASSNAME missing");
        assertEquals("Payment", classNameElement.getAsString());
    }

    @Test
    public void paymentDeserializationTest() {
        PrivateBank privateBank = new PrivateBank("Private Bank", 0.5, 0.5, directory);
        assertDoesNotThrow(() -> privateBank.createAccount("payment"));
        Payment transaction = new Payment("26.11.2023", 1000, "Payment", 0.5, 0.5);
        assertDoesNotThrow(() -> privateBank.addTransaction("payment", transaction));
        List<Transaction> transactionList = privateBank.getAccountsToTransactions().get("payment");
        assertEquals(1, transactionList.size());
        Transaction transaction1 = transactionList.get(0);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Payment.class, new TransactionSerializer());
        Gson gson = gsonBuilder.create();
        String json = gson.toJson(transaction1);
        String comparison = "{" +
                "\"CLASSNAME\":\"Payment\"," +
                "\"INSTANCE\":{" +
                "\"incomingInterest\":0.5," +
                "\"outgoingInterest\":0.5," +
                "\"date\":\"26.11.2023\"," +
                "\"amount\":1000.0," +
                "\"description\":\"Payment\"" +
                "}" + "}";
        assertEquals(comparison, json);
        Payment deserializedTransaction = gson.fromJson(json, Payment.class);

        assertEquals("26.11.2023", deserializedTransaction.getDate());
        assertEquals(1000, deserializedTransaction.getAmount());
        assertEquals("Payment", deserializedTransaction.getDescription());
        assertEquals(0.5, deserializedTransaction.getIncomingInterest());
        assertEquals(0.5, deserializedTransaction.getOutgoingInterest());
    }
}
