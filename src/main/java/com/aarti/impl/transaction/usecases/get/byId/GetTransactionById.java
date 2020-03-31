package com.aarti.impl.transaction.usecases.get.byId;

import com.aarti.impl.transaction.domain.Transaction;

public interface GetTransactionById {
    Transaction searchTransactionById(long id);
}
