package com.aarti.adviqo.transaction.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTransactionByIdResponse {
    private double amount;
    private String type;
    private Long parentId;
}
