package com.aarti.adviqo.transaction.repository;

import com.aarti.adviqo.transaction.domain.Transaction;

import javax.validation.constraints.NotNull;
import java.util.*;

public class TransactionRepository {
    Map<Long, Transaction> transactions = new HashMap<Long, Transaction>();
    Map<Long, LinkedList> linkedTransactions = new HashMap<Long, LinkedList>();

    public void add(@NotNull Transaction transaction) {
        transactions.put(transaction.getId(), transaction);
        if(transaction.getParentId() != 0) {
            LinkedList<Long> list;
            if(linkedTransactions.get(transaction.getParentId()) != null) {
                 list = linkedTransactions.get(transaction.getParentId());
                list.add(transaction.getId());
            } else {
                list = new LinkedList<>();
                list.add(transaction.getId());
            }
            linkedTransactions.put(transaction.getParentId(), list);
        }
        System.out.println(transactions);
        System.out.println(linkedTransactions);
    }

    public Transaction getTransactionById(long id) {
        return transactions.get(id);
    }

    public LinkedList<Long> getAllLinkedTransactions(long id) {
        LinkedList<Long> list = new LinkedList<>();

        list = getLinkedTransactions(id, list);

        return list;
    }

    private LinkedList<Long> getLinkedTransactions(Long currentTransaction, LinkedList<Long> list) {
        LinkedList<Long> childTransactions = linkedTransactions.getOrDefault(currentTransaction, new LinkedList<Long>());
        LinkedList<Long> tempChildren = (LinkedList<Long>) childTransactions.clone();

        list.add(currentTransaction);
        if(childTransactions.size() > 0) {
            tempChildren.forEach((transaction) -> {
                getLinkedTransactions(childTransactions.pop(), list);
            });
        }

        return list;
    }

    public Double getSum(Long id) {
        LinkedList<Long> transactionIds = getAllLinkedTransactions(id);
        Double sum = 0.0;

        for(Long currentId : transactionIds)
            sum += transactions.get(currentId).getAmount();

        return sum;
    }

}
