package com.aarti.adviqo.transaction.usecases.get.byId;

import com.aarti.adviqo.transaction.domain.Transaction;
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
