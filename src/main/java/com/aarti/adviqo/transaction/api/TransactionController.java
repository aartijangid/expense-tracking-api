package com.aarti.adviqo.transaction.api;

import com.aarti.adviqo.transaction.api.dto.*;
import com.aarti.adviqo.transaction.api.exception.NotFoundError;
import com.aarti.adviqo.transaction.domain.Transaction;
import com.aarti.adviqo.transaction.repository.exception.InvalidTransactionException;
import com.aarti.adviqo.transaction.repository.exception.TransactionNotFoundException;
import com.aarti.adviqo.transaction.usecases.add.SaveTransactionUseCase;
import com.aarti.adviqo.transaction.usecases.get.byId.GetTransactionByIdUseCase;
import com.aarti.adviqo.transaction.usecases.get.byType.GetTransactionByTypeUseCase;
import com.aarti.adviqo.transaction.usecases.get.sum.GetTotalTransactionAmountUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/transactionservice")
public class TransactionController {

    private SaveTransactionUseCase saveTransactionUseCase;
    private GetTransactionByIdUseCase getTransactionByIdUseCase;
    private GetTransactionByTypeUseCase getTransactionByTypeUseCase;
    private GetTotalTransactionAmountUseCase getTotalTransactionAmountUseCase;

    public TransactionController(SaveTransactionUseCase saveTransactionUseCase,
                                 GetTransactionByIdUseCase getTransactionByIdUseCase,
                                 GetTransactionByTypeUseCase getTransactionByTypeUseCase,
                                 GetTotalTransactionAmountUseCase getTotalTransactionAmountUseCase
    ) {
        this.saveTransactionUseCase = saveTransactionUseCase;
        this.getTransactionByIdUseCase = getTransactionByIdUseCase;
        this.getTransactionByTypeUseCase = getTransactionByTypeUseCase;
        this.getTotalTransactionAmountUseCase = getTotalTransactionAmountUseCase;
    }

    @PutMapping(value = "/transaction/{transaction_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public CreateTransactionResponse addTransaction(@RequestBody TransactionRequest transactionRequest, @PathVariable Long transaction_id){
        try {
            System.out.println("transactionId -> " + transaction_id
                    + " transactionRequest.getType() -> " + transactionRequest.getType()
                    + " transactionRequest.getAmount() -> "+ transactionRequest.getAmount()
                    + " transactionRequest.getParentId() -> "+ transactionRequest.getParent_id());
            saveTransactionUseCase.run(transaction_id,
                    transactionRequest.getType(),
                    transactionRequest.getAmount(),
                    transactionRequest.getParent_id());
            return new CreateTransactionResponse("OK");
        } catch (InvalidTransactionException e) {
            throw new InvalidTransactionException();
        }
    }

    @GetMapping(value = "/transaction/{transaction_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTransactionByIdResponse getTransactionById(@PathVariable Long transaction_id){
        try {
            Transaction transaction = getTransactionByIdUseCase.run(transaction_id);
            System.out.println(transaction.getParentId());
            return new GetTransactionByIdResponse(transaction.getAmount(), transaction.getType(), java.util.Optional.of(transaction.getParentId()));
        } catch (TransactionNotFoundException e) {
            throw new NotFoundError(e.getMessage());
        }
    }

    @GetMapping(value = "/types/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTransactionByTypeResponse getTransactionByType(@PathVariable String type) {
        try {
            return new GetTransactionByTypeResponse(getTransactionByTypeUseCase.run(type));
        } catch (TransactionNotFoundException e) {
            throw new NotFoundError(e.getMessage());
        }
    }

    @GetMapping(value = "/sum/{transaction_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetTotalTransactionAmountResponse getTotalTransactionAmount(@PathVariable Long transaction_id){
        try {
            return new GetTotalTransactionAmountResponse(getTotalTransactionAmountUseCase.run(transaction_id));
        } catch (TransactionNotFoundException e) {
            throw new NotFoundError(e.getMessage());
        }
    }
}
