package com.aarti.impl.transaction.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTotalTransactionAmountResponse {
    Double sum;
}
