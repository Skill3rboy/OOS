package bank;


import java.util.*;
import bank.exceptions.*;
import java.io.*;
import java.nio.file.*;
import com.google.gson.*;
import com.google.gson.reflect.*;
import java.lang.reflect.*;

public class PrivateBank implements Bank{
    /**
     * Name of the Private Bank
     */
    private String name;
    /**
     * Input fees of the Transaction
     * Between 0 and 1
     * Identical to the incomingInterest of Payment
     */
    private double incomingInterest;
    /**
     * Output fees of the Transaction
     * Between 0 and 1
     * Identical to the outgoingInterest of Payment
     */
    private double outgoingInterest;

    /**
     * Connection between Accounts and Transaction
     * every Account can have multiple Transactions
     */
    private Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    private String directory;


    /**Getter for the Name
     * @return name
     */
    public String getName(){return this.name;}

    /**Setter for the Name
     * @param name
     */
    public void  setName(String name){this.name=name;}

    /** Getter for the IncomingInterest
     * @return incomingInterest
     */
    public double getIncomingInterest() {
        return this.incomingInterest;
    }

    /**Setter for the Incominginterest
     * @param incomingInterest
     */
    public void setIncomingInterest(double incomingInterest){this.incomingInterest=incomingInterest;}

    /**Getter for the Outgoinginterest
     * @return outgoingInterest
     */
    public double getOutgoingInterest() {
        return this.outgoingInterest;
    }

    /**Setter for the OutgoingInterest
     * @param outgoingInterest
     */
    public void setOutgoingInterest(double outgoingInterest)
    {
        this.outgoingInterest=outgoingInterest;
    }

    /**Getter for the map AccountsToTransaction
     * @return getAccountsToTransaction
     */
    public Map<String, List<Transaction>> getAccountsToTransactions(){
        return this.accountsToTransactions;
    }

    /**
     * @return directory
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * @param directory
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /** Standard Constructor for a Private Bank
     * @param name
     * @param incomingInterest
     * @param outgoingInterest
     */
    public PrivateBank(String name, double incomingInterest, double outgoingInterest, String directory){
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
        setDirectory(directory);

    }

    /** Copyconstructor for a Private Bank
     * @param privateBank
     */
    public PrivateBank(PrivateBank privateBank) throws IOException {
        this(privateBank.getName(),privateBank.getIncomingInterest(),privateBank.getOutgoingInterest(), privateBank.getDirectory());
        this.accountsToTransactions = privateBank.getAccountsToTransactions();
        readAccounts();
        setDirectory(privateBank.getDirectory());
    }


    /**
     * @return String of all the Attributs from a Private Bank
     */
    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        Set<String> setKey = accountsToTransactions.keySet();
        for (String key : setKey)
        {
            str.append(key).append(" => \n");
            List<Transaction> transactionsList = accountsToTransactions.get(key);
            for (Transaction transaction : transactionsList)
            {
                str.append("\t\t").append(transaction);
            }
        }
        return "Name: " + getName() +
                "\n IncomingInterest: " + getIncomingInterest() +
                "\n OutgoingInterest: " + getOutgoingInterest() +
                "\n " + str;
    }

    /**
     * @param object which should be checked
     * @return True if equal
     */
    @Override
    public boolean equals(Object object){
        if(object instanceof PrivateBank)
        {
            return (getName().equals(((PrivateBank) object).getName())) &&
                    (Double.compare(getIncomingInterest(),((PrivateBank) object).getIncomingInterest()) ==0) &&
                    (Double.compare(getOutgoingInterest(),((PrivateBank) object).getOutgoingInterest())==0) &&
                    (getAccountsToTransactions().equals(((PrivateBank) object).getAccountsToTransactions()));
        }
        return false;
    }

    /** Adds an account to the Private Bank
     * @param account the account to be added
     * @throws AccountAlreadyExistsException
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if (!(accountsToTransactions.containsKey(account))) {
            accountsToTransactions.put(account, List.of());
        } else {
            throw new AccountAlreadyExistsException();
        }
        try{
            this.writeAccount(account);
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }

    /** Adds an account to the Private Bank
     * @param account      the account to be added
     * @param transactions a list of already existing transactions which should be added to the newly created account
     * @throws AccountAlreadyExistsException
     * @throws TransactionAlreadyExistException
     * @throws TransactionAttributeException
     */
    @Override
    public void createAccount(String account,List<Transaction> transactions)
            throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException {
        if(accountsToTransactions.containsKey(account)){
            throw new AccountAlreadyExistsException();
        }
        Set<Transaction> duplicatedTransactions = new HashSet();
        for(Transaction transaction : transactions){
            if(!duplicatedTransactions.add(transaction)){
                throw new TransactionAlreadyExistException();
            }
            if(!transaction.validateTransactionAttributes()){
                throw new TransactionAttributeException();
            }
        }
        accountsToTransactions.put(account, transactions);

        try {
            this.writeAccount(account);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }

    /** Add a Transaction to an Account
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException
     * @throws AccountDoesNotExistException
     * @throws TransactionAttributeException
     */
    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IncomingException, OutgoingException, IOException {
        if(!(accountsToTransactions.containsKey(account)))
        {
            throw new AccountDoesNotExistException();
        }
        else if(!(transaction.validateTransactionAttributes()))
        {
            throw new TransactionAttributeException();
        }
        else if(containsTransaction(account,transaction))
        {
            throw new TransactionAlreadyExistException();
        }
        else{
            if(transaction instanceof Payment payment)
            {
                payment.setIncomingInterest(getIncomingInterest());
                payment.setOutgoingInterest(getOutgoingInterest());
            }
            List<Transaction> transactionsList = new ArrayList<>(accountsToTransactions.get(account));
            transactionsList.add(transaction);
            accountsToTransactions.put(account, transactionsList);
            writeAccount(account);
        }
    }

    /** Remove a Transaction from an Account
     * @param account     the account from which the transaction is removed
     * @param transaction the transaction which is removed from the specified account
     * @throws AccountDoesNotExistException
     * @throws TransactionDoesNotExistException
     */
    @Override
    public void removeTransaction(String account, Transaction transaction)
            throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if(!(accountsToTransactions.containsKey(account)))
         {
             throw new AccountDoesNotExistException();
         }else{
            if(containsTransaction(account,transaction))
            {
                List<Transaction> transactions =  accountsToTransactions.get(account);
                accountsToTransactions.get(account).remove(transaction);
                accountsToTransactions.put(account,transactions);
                writeAccount(account);
            } else {
                throw new TransactionDoesNotExistException();
            }
         }
     }

    /**Check if the Account already has the Transaction
     * @param account     the account from which the transaction is checked
     * @param transaction the transaction to search/look for
     * @return True if already exist
     */
     @Override
    public  boolean containsTransaction(String account, Transaction transaction){
         List<Transaction> transactions =  accountsToTransactions.get(account);
         for(Transaction pTransaction: transactions)
         {
             if(pTransaction.equals(transaction))
             {
                return true;
             }
         }
         return false;
     }

    /**
     * @param account the selected account
     * @return Calculated Account balance
     */
     @Override
    public double getAccountBalance(String account){
    double ans=0;
    for(Transaction transactions: accountsToTransactions.get(account))
    {
        ans+= transactions.calculate();
    }
    return ans;
     }

    /**
     * @param account the selected account
     * @return Transaction list
     */
     @Override
    public List<Transaction> getTransactions(String account)
     {
        return  accountsToTransactions.get(account);
     }

    /**
     * @param account the selected account
     * @param asc     selects if the transaction list is sorted in ascending or descending order
     * @return List of Transactions Sorted
     */
     @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc)
     {
         List<Transaction> transactions = getTransactions(account);
         if(asc)
         {
             transactions.sort(Comparator.comparingDouble(Transaction::getAmount));
         }
         else
         {
             transactions.sort(Comparator.comparingDouble(Transaction::getAmount).reversed());
         }
         return transactions;
     }

    /**
     * @param account  the selected account
     * @param positive selects if positive or negative transactions are listed
     * @return Transaction List filtered by Positive or Negative amount
     */
     @Override
    public  List<Transaction> getTransactionsByType(String account, boolean positive)
     {
         List<Transaction> transactions = getTransactions(account);
         List<Transaction> returnList = new ArrayList<>();
         for(Transaction transaction: transactions)
         {
             if(transaction.calculate()>=0 && positive || transaction.calculate()<0 && !positive)
             {
                 returnList.add(transaction);
             }
         }
         return returnList;
     }


    /**Read AccountList from Json file
     * @throws IOException
     */
     public void readAccounts() throws IOException {
         for(String account : accountsToTransactions.keySet()){
             String fileName =  account + ".json";
             Path filePath = Paths.get(directory, fileName);
             if(Files.exists(filePath)) {
                 try(Reader reader = Files.newBufferedReader(filePath)) {

                     Gson gson = new GsonBuilder().registerTypeAdapter(Transaction.class, new TransactionSerializer()).create();
                     Type transactionListType = new TypeToken<List<Transaction>>() {}.getType();
                     List<Transaction> transactions = gson.fromJson(reader, transactionListType);
                     accountsToTransactions.put(account, transactions);
                 }
             }
         }
     }

    /**Write the accounts in a Json file
     * @param account
     * @throws IOException
     */
     public void writeAccount(String account) throws IOException
     {
         String fileName =  account + ".json";
         Path filePath = Paths.get(directory, fileName);
         Path directoryPath = filePath.getParent();
         Files.createDirectories(directoryPath);

         try(Writer writer = Files.newBufferedWriter(filePath)) {

             Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(OutgoingTransfer.class, new TransactionSerializer()).registerTypeAdapter(Payment.class, new TransactionSerializer()).registerTypeAdapter(IncomingTransfer.class, new TransactionSerializer()).setPrettyPrinting().create();

             List<Transaction> transactions = accountsToTransactions.get(account);

             if(transactions != null) {
                 gson.toJson(transactions, writer);
             }
         } catch(IOException exp){
             throw new IOException();
         }
     }

    /**Getter for Directory
     * @return directory
     */
    public String getDirectoryName() {
         return this.directory;
    }
}
