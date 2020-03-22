package com.aarti.adviqo.transaction.usecases.add;

import com.aarti.adviqo.transaction.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveTransactionUseCaseTest {
    private SaveTransactionUseCase saveTransactionUseCase;

    @Mock
    private static AddNewTransaction addNewTransaction;

    @BeforeEach
    void SetUp(){
        saveTransactionUseCase = new SaveTransactionUseCase(addNewTransaction);

    }

    @Test
    void saveTransaction() {
        Transaction transaction = new Transaction(1, 10.0, "cars");
        doNothing().when(addNewTransaction).createTransaction(any());

        saveTransactionUseCase.saveTransaction((long)1, "cars", 10.0, (long) 1);
        verify(addNewTransaction).createTransaction(any());

    }
}