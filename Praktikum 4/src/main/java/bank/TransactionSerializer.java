package bank;

import bank.exceptions.IncomingException;
import bank.exceptions.OutgoingException;
import bank.exceptions.TransactionAttributeException;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Class for Transaction Serialization
 */
public class TransactionSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {
    /**
     * @param jsonElement
     * @param type
     * @param jsonDeserializationContext
     * @return  Transaction Obeject
     * @throws JsonParseException
     */
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        if(jsonObject.get("CLASSNAME").getAsString().equals("Payment"))
        {
            return new Payment(jsonObject.get("date").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("incomingInterest").getAsDouble(),
                    jsonObject.get("outgoingInterest").getAsDouble());
        } else if (jsonObject.get("CLASSNAME").getAsString().equals("IncomingTransfer")) {
            return new IncomingTransfer(
                    jsonObject.get("date").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("sender").getAsString(),
                    jsonObject.get("recipient").getAsString());
        } else{
            return new OutgoingTransfer(
                    jsonObject.get("date").getAsString(),
                    jsonObject.get("amount").getAsDouble(),
                    jsonObject.get("description").getAsString(),
                    jsonObject.get("sender").getAsString(),
                    jsonObject.get("recipient").getAsString());
        }

    }

    /**
     * @param transaction
     * @param type
     * @param jsonSerializationContext
     * @return JsonEleemtn
     */
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonTransaction = new JsonObject();
        JsonObject jsonObject = new JsonObject();

        if (transaction instanceof Transfer transfer)
        {
            jsonTransaction.addProperty("sender", transfer.getSender());
            jsonTransaction.addProperty("recipient", transfer.getRecipient());
        }
        else if (transaction instanceof Payment payment)
        {
            jsonTransaction.addProperty("incomingInterest", payment.getIncomingInterest());
            jsonTransaction.addProperty("outgoingInterest", payment.getOutgoingInterest());
        }

        jsonTransaction.addProperty("date", transaction.getDate());
        jsonTransaction.addProperty("amount", transaction.getAmount());
        jsonTransaction.addProperty("description", transaction.getDescription());

        jsonObject.addProperty("CLASSNAME", transaction.getClass().getSimpleName());
        jsonObject.add("INSTANCE", jsonTransaction);

        return jsonObject;
    }
}
