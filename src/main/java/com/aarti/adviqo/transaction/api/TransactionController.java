package com.aarti.adviqo.transaction.api;

import com.aarti.adviqo.transaction.api.dto.*;
import com.aarti.adviqo.transaction.api.exception.NotFoundError;
import com.aarti.adviqo.transaction.domain.Transaction;
import com.aarti.adviqo.transaction.repository.exception.InvalidTransactionException;
import com.aarti.adviqo.transaction.repository.exception.TransactionNotFoundException;
import com.aarti.adviqo.transaction.usecases.add.SaveTransactionUseCase;
import com.aarti.adviqo.transaction.usecases.get.byId.GetTransactionById;
import com.aarti.adviqo.transaction.usecases.get.byType.GetTransactionByType;
import com.aarti.adviqo.transaction.usecases.get.sum.GetTotalTransactionAmount;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactionservice")
public class TransactionController {

    private SaveTransactionUseCase saveTransactionUseCase;
    private GetTransactionById getTransactionById;
    private GetTransactionByType getTransactionByType;
    private GetTotalTransactionAmount getTotalTransactionAmount;

    public TransactionController(SaveTransactionUseCase saveTransactionUseCase,
                                 GetTransactionById getTransactionById,
                                 GetTransactionByType getTransactionByType,
                                 GetTotalTransactionAmount getTotalTransactionAmount
    ) {
        this.saveTransactionUseCase = saveTransactionUseCase;
        this.getTransactionById = getTransactionById;
        this.getTransactionByType = getTransactionByType;
        this.getTotalTransactionAmount = getTotalTransactionAmount;
    }

    @PutMapping(value = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CreateTransactionResponse addTransaction(@RequestBody TransactionRequest transactionRequest, @PathVariable Long transactionId){
        try {
            saveTransactionUseCase.saveTransaction(transactionId,
                    transactionRequest.getType(),
                    transactionRequest.getAmount(),
                    transactionRequest.getParentId());
            return new CreateTransactionResponse("OK");
        } catch (InvalidTransactionException e) {
            throw new InvalidTransactionException();
        }
    }

    @GetMapping(value = "/transaction/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTransactionByIdResponse getTransactionById(@PathVariable Long transactionId){
        try {
            Transaction transaction = getTransactionById.searchTransactionById(transactionId);
            return new GetTransactionByIdResponse(transaction.getAmount(), transaction.getType());
        } catch (TransactionNotFoundException e) {
            throw new NotFoundError(e.getMessage());
        }
    }

    @GetMapping(value = "/types/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTransactionByTypeResponse getTransactionByType(@PathVariable String type) {
        try {
            return new GetTransactionByTypeResponse(getTransactionByType.searchTransactionOfType(type));
        } catch (TransactionNotFoundException e) {
            throw new NotFoundError(e.getMessage());
        }
    }

    @GetMapping(value = "/sum/{transactionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTotalTransactionAmountResponse getTotalTransactionAmount(@PathVariable Long transactionId){
        try {
            return new GetTotalTransactionAmountResponse(getTotalTransactionAmount.getTransactionAmount(transactionId));
        } catch (Exception e) {
            throw e;
        }
    }
}
