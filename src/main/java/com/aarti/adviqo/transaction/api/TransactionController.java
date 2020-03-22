package com.aarti.adviqo.transaction.api;

import com.aarti.adviqo.transaction.usecases.add.SaveTransactionUseCase;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private SaveTransactionUseCase saveTransactionUseCase;

    public TransactionController(SaveTransactionUseCase saveTransactionUseCase) {
        this.saveTransactionUseCase = saveTransactionUseCase;
    }

    @PutMapping(value = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TransactionResponse> addTransaction(@RequestBody TransactionRequest transactionRequest, @PathVariable String transactionId){
        try {
            System.out.println(transactionId + transactionRequest.getType() +
                    transactionRequest.getAmount() + transactionRequest.getParentId());
            saveTransactionUseCase.saveTransaction(Long.parseLong(transactionId),
                    transactionRequest.getType(),
                    transactionRequest.getAmount(),
                    transactionRequest.getParentId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("test");
        return new ResponseEntity<>(new TransactionResponse("OK"), HttpStatus.OK);
    }

    @AllArgsConstructor
    @Data
    private class TransactionResponse {
        private String status;
    }
}
