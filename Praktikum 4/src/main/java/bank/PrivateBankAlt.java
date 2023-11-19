package bank;

import bank.exceptions.*;
import java.util.*;

public class PrivateBankAlt implements Bank{
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

    /**Setter for the map AccountsToTranscation
     * @param map
     */
    public void setAccountsToTransactions(Map<String, List<Transaction>> map)
    {
        this.accountsToTransactions =map;
    }

    /** Standard Constructor for a Private Bank
     * @param name
     * @param incomingInterest
     * @param outgoingInterest
     */
    public PrivateBankAlt(String name, double incomingInterest, double outgoingInterest){
        setName(name);
        setIncomingInterest(incomingInterest);
        setOutgoingInterest(outgoingInterest);
    }

    /** Copyconstructor for a Private Bank
     * @param privateBankAlt
     */
    public PrivateBankAlt(PrivateBankAlt privateBankAlt){
        this(privateBankAlt.getName(),privateBankAlt.getIncomingInterest(),privateBankAlt.getOutgoingInterest());
        this.accountsToTransactions = privateBankAlt.getAccountsToTransactions();
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
        if(object instanceof PrivateBankAlt)
        {
            return (getName().equals(((PrivateBankAlt) object).getName())) &&
                    (Double.compare(getIncomingInterest(),((PrivateBankAlt) object).getIncomingInterest()) ==0) &&
                    (Double.compare(getOutgoingInterest(),((PrivateBankAlt) object).getOutgoingInterest())==0) &&
                    (getAccountsToTransactions().equals(((PrivateBankAlt) object).getAccountsToTransactions()));
        }
        return false;
    }

    /** Adds an account to the Private Bank
     * @param account the account to be added
     * @throws AccountAlreadyExistsException
     */
    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException{
        if(!(accountsToTransactions.containsKey(account))){
            accountsToTransactions.put(account, List.of());
        }
        else{
            throw new AccountAlreadyExistsException();
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
            throws AccountAlreadyExistsException,TransactionAlreadyExistException, TransactionAttributeException{
        if(accountsToTransactions.containsKey(account)){
            throw new AccountAlreadyExistsException();
        }
        else{
            Set<Transaction> dupTransactions = new HashSet<>();
            for(Transaction pTransactions: transactions)
            {
                if(!dupTransactions.add(pTransactions))
                {
                    throw new TransactionAlreadyExistException();
                }
                if(!pTransactions.validateTransactionAttributes()){
                    throw new TransactionAttributeException();
                }
            }
            accountsToTransactions.put(account, transactions);
        }
    }

    /** Add a Transcaction to an Account
     * @param account     the account to which the transaction is added
     * @param transaction the transaction which should be added to the specified account
     * @throws TransactionAlreadyExistException
     * @throws AccountDoesNotExistException
     * @throws TransactionAttributeException
     */
    @Override
    public void addTransaction(String account, Transaction transaction)
            throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IncomingException, OutgoingException {
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
            throws AccountDoesNotExistException, TransactionDoesNotExistException{
        if(!(accountsToTransactions.containsKey(account)))
        {
            throw new AccountDoesNotExistException();
        }else{
            List<Transaction> transactions =  accountsToTransactions.get(account);
            boolean found= false;
            for(Transaction pTransaction: transactions)
            {
                if (pTransaction.equals(transaction)) {
                    found = true;
                    break;
                }
            }
            if(found) {
                accountsToTransactions.get(account).remove(transaction);
            }else {
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
     * @return Calculated AccoutBalance
     */
    @Override
    public double getAccountBalance(String account){
        double ans=0;
        for(Transaction transactions: accountsToTransactions.get(account))
        {
            if(!(transactions instanceof Transfer))
            {
                ans+= transactions.calculate();
            } else if (((Transfer) transactions).getSender().equals(account)) {
                ans -= transactions.calculate();

            }
            else {
                ans += transactions.calculate();
            }
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
     * @return Transaction List filtered by Positve or Negative amount
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
}
