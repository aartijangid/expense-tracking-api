package com.aarti.adviqo.transaction.repository;

import com.aarti.adviqo.transaction.domain.Transaction;
import com.aarti.adviqo.transaction.repository.exception.ParentTransactionNotFound;
import com.aarti.adviqo.transaction.repository.exception.TransactionNotFoundException;
import com.aarti.adviqo.transaction.usecases.add.AddNewTransaction;
import com.aarti.adviqo.transaction.usecases.get.byId.GetTransactionById;
import com.aarti.adviqo.transaction.usecases.get.byType.GetTransactionByType;
import com.aarti.adviqo.transaction.usecases.get.sum.GetTotalTransactionAmount;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository implements AddNewTransaction,
        GetTransactionById,
        GetTransactionByType,
        GetTotalTransactionAmount
{
    Map<Long, Transaction> transactionStore = new HashMap<>();
    Map<Long, LinkedList<Long>> linkedTransactions = new HashMap<>();

    @Override
    public void createTransaction(Transaction transaction) throws ParentTransactionNotFound {
        if(transaction.getParentId() != 0){
            if(isTransactionPresent(transaction.getParentId())){
                if(transaction.getParentId() != 0) {
                    LinkedList<Long> list;
                    if(linkedTransactions.get(transaction.getParentId()) != null) {
                        list = linkedTransactions.get(transaction.getParentId());
                    } else {
                        list = new LinkedList<>();
                    }
                    list.add(transaction.getId());
                    linkedTransactions.put(transaction.getParentId(), list);
                }
            }else
                throw new ParentTransactionNotFound();
        }
        transactionStore.put(transaction.getId(), transaction);
    }

    private boolean isTransactionPresent(long parentId) {
        if(transactionStore.containsKey(parentId))
            return true;
        return false;
    }

    @Override
    public Transaction searchTransactionById(long id) throws TransactionNotFoundException {
        if(transactionStore.containsKey(id)){
            return transactionStore.get(id);
        } else
            throw new TransactionNotFoundException();

    }

    private LinkedList<Long> getAllLinkedTransactions(long id) {
        return getLinkedTransactions(id, new LinkedList<>());
    }

    private LinkedList<Long> getLinkedTransactions(Long currentTransaction, LinkedList<Long> list) {
        LinkedList<Long> childTransactions = linkedTransactions.getOrDefault(currentTransaction, new LinkedList<>());
        LinkedList<?> tempChildren = new LinkedList<>(childTransactions);

        list.add(currentTransaction);
        if(childTransactions.size() > 0) {
            tempChildren.forEach((transaction) -> getLinkedTransactions(childTransactions.pop(), list));
        }
        return list;
    }

    @Override
    public Double getTransactionAmount(Long id) {
        LinkedList<Long> transactionIds = getAllLinkedTransactions(id);
        double sum = 0.0;

        for(Long currentId : transactionIds)
            sum += transactionStore.get(currentId).getAmount();

        return sum;
    }

    @Override
    public ArrayList<Long> searchTransactionOfType(String type) throws TransactionNotFoundException{
        return (ArrayList<Long>) transactionStore.entrySet()
                .stream()
                .filter(e -> e.getValue().getType().equals(type))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
