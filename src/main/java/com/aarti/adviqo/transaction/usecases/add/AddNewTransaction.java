package com.aarti.adviqo.transaction.usecases.add;

import com.aarti.adviqo.transaction.domain.Transaction;

public interface AddNewTransaction {
    void createTransaction(Transaction transaction);
}
