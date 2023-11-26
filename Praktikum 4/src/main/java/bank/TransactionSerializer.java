package bank;

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
        JsonObject instance = jsonObject.get("INSTANCE").getAsJsonObject();
        String classname = jsonObject.get("CLASSNAME").getAsString();
        if(classname.equals("Payment"))
        {
            return new Payment(
                    instance.get("date").getAsString(),
                    instance.get("amount").getAsDouble(),
                    instance.get("description").getAsString(),
                    instance.get("incomingInterest").getAsDouble(),
                    instance.get("outgoingInterest").getAsDouble());
        } else if (classname.equals("IncomingTransfer")) {
            return new IncomingTransfer(
                    instance.get("date").getAsString(),
                    instance.get("amount").getAsDouble(),
                    instance.get("description").getAsString(),
                    instance.get("sender").getAsString(),
                    instance.get("recipient").getAsString());
        } else{
            return new OutgoingTransfer(
                    instance.get("date").getAsString(),
                    instance.get("amount").getAsDouble(),
                    instance.get("description").getAsString(),
                    instance.get("sender").getAsString(),
                    instance.get("recipient").getAsString());

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
