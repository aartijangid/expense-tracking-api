package com.aarti.adviqo.transaction.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
public class GetTransactionByIdResponse {
    private double amount;
    private String type;
    private Long parentId;

    public GetTransactionByIdResponse(double amount, String type) {
        this.amount = amount;
        this.type = type;
    }
}
