package com.aarti.adviqo.transaction.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class TransactionRequest {
    @JsonProperty(required = true)
    private double amount;
    @JsonProperty(required = true)
    private String type;
    @JsonProperty
    private long parentId;
}
