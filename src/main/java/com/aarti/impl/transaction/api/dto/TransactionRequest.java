package com.aarti.impl.transaction.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransactionRequest {
    @JsonProperty(required = true)
    private double amount;
    @JsonProperty(required = true)
    private String type;
    @JsonProperty
    private long parent_id;

    public TransactionRequest(double amount, String type, long parent_id) {
        this.amount = amount;
        this.type = type;
        this.parent_id = parent_id;
    }
}
