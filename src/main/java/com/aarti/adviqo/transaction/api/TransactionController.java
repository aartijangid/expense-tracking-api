package com.aarti.adviqo.transaction.api;

import com.aarti.adviqo.transaction.api.dto.CreateTransactionResponse;
import com.aarti.adviqo.transaction.api.dto.GetTransactionByIdResponse;
import com.aarti.adviqo.transaction.domain.Transaction;
import com.aarti.adviqo.transaction.usecases.add.SaveTransactionUseCase;
import com.aarti.adviqo.transaction.usecases.get.byId.GetTransactionById;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactionservice")
public class TransactionController {

    private SaveTransactionUseCase saveTransactionUseCase;
    private GetTransactionById getTransactionById;

    public TransactionController(SaveTransactionUseCase saveTransactionUseCase, GetTransactionById getTransactionById) {
        this.saveTransactionUseCase = saveTransactionUseCase;
        this.getTransactionById = getTransactionById;
    }

    @PutMapping(value = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CreateTransactionResponse addTransaction(@RequestBody TransactionRequest transactionRequest, @PathVariable Long transactionId){

            saveTransactionUseCase.saveTransaction(transactionId,
                    transactionRequest.getType(),
                    transactionRequest.getAmount(),
                    transactionRequest.getParentId());

        return new CreateTransactionResponse("OK");
    }

    @GetMapping(value = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTransactionByIdResponse getTransactionBy(@PathVariable Long transactionId){
        try {
            Transaction transaction = getTransactionById.searchTransactionById(transactionId);
            return new GetTransactionByIdResponse(transaction.getAmount(), transaction.getType());
        } catch (Exception e) {
            throw e;
        }
    }
}
