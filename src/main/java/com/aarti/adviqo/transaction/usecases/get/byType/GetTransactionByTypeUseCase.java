package com.aarti.adviqo.transaction.usecases.get.byType;

import com.aarti.adviqo.transaction.domain.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GetTransactionByTypeUseCase {
    private final GetTransactionByType getTransactionByType;

    public GetTransactionByTypeUseCase(GetTransactionByType getTransactionByType) {
        this.getTransactionByType = getTransactionByType;
    }

    public ArrayList<Long> run(String type) {
        return getTransactionByType.searchTransactionOfType(type);
    }
}
