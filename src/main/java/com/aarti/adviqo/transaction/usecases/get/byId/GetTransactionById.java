package com.aarti.adviqo.transaction.usecases.get.byId;

import com.aarti.adviqo.transaction.repository.exception.TransactionNotFoundException;
import com.aarti.adviqo.transaction.domain.Transaction;

public interface GetTransactionById {
    Transaction searchTransactionById(long id) throws TransactionNotFoundException;
}
