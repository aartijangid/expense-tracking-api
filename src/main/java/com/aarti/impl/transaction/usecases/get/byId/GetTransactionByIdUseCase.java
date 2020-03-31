package com.aarti.impl.transaction.usecases.get.byId;

import com.aarti.impl.transaction.domain.Transaction;
import org.springframework.stereotype.Service;

@Service
public class GetTransactionByIdUseCase {
    private final GetTransactionById getTransactionById;

    public GetTransactionByIdUseCase(GetTransactionById getTransactionById) {
        this.getTransactionById = getTransactionById;
    }

    public Transaction run(long id) {
        return getTransactionById.searchTransactionById(id);
    }
}
