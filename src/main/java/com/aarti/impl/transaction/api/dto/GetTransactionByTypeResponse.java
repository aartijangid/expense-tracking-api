package com.aarti.impl.transaction.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetTransactionByTypeResponse {
    private ArrayList<Long> transaction_id;
}
