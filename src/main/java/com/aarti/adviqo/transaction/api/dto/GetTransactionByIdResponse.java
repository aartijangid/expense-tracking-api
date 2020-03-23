package com.aarti.adviqo.transaction.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
public class GetTransactionByIdResponse {
    private double amount;
    private String type;
    private Optional<Long> parent_id;

    public GetTransactionByIdResponse(double amount, String type, Optional<Long> parent_id) {
        this.amount = amount;
        this.type = type;
        this.parent_id = parent_id;
    }
}
