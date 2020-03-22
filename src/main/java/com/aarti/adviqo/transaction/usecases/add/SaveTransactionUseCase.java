package com.aarti.adviqo.transaction.usecases.add;

import com.aarti.adviqo.transaction.domain.Transaction;
import org.springframework.stereotype.Service;

@Service
public class SaveTransactionUseCase {
    private final AddNewTransaction addNewTransaction;

    public SaveTransactionUseCase(AddNewTransaction addNewTransaction){
        this.addNewTransaction = addNewTransaction;
    }

    public void saveTransaction(Long id, String type, double amount, Long parentId){
        Transaction newTransaction = new Transaction(id, amount, type, parentId);
        addNewTransaction.createTransaction(newTransaction);
    }
}
