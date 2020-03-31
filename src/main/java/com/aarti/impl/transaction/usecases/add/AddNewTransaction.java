package com.aarti.impl.transaction.usecases.add;

import com.aarti.impl.transaction.domain.Transaction;

public interface AddNewTransaction {
    void createTransaction(Transaction transaction);
}
