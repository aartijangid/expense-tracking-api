package com.aarti.adviqo.transaction.usecases.get.sum;

import org.springframework.stereotype.Service;

@Service
public class GetTotalTransactionAmountUseCase {
    private final GetTotalTransactionAmount getTotalTransactionAmount;

    public GetTotalTransactionAmountUseCase(GetTotalTransactionAmount getTotalTransactionAmount) {
        this.getTotalTransactionAmount = getTotalTransactionAmount;
    }

    public Double run(Long id) {
        return getTotalTransactionAmount.getTransactionAmount(id);
    }
}
