package com.aarti.impl.transaction.usecases.add;

import com.aarti.impl.transaction.domain.Transaction;
import org.springframework.stereotype.Service;

@Service
public class SaveTransactionUseCase {
    private final AddNewTransaction addNewTransaction;

    public SaveTransactionUseCase(AddNewTransaction addNewTransaction){
        this.addNewTransaction = addNewTransaction;
    }

    public void run(Long id, String type, double amount, Long parentId) {
        Transaction newTransaction = new Transaction(id, amount, type, parentId);
        addNewTransaction.createTransaction(newTransaction);
    }
}
