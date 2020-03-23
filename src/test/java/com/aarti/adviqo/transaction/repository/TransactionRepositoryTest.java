package com.aarti.adviqo.transaction.repository;

import com.aarti.adviqo.transaction.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionRepositoryTest {

    private TransactionRepository transactionRepository;
    private Transaction transaction1;
    private Transaction transaction2;
    private Transaction transaction3;
    private Transaction transaction4;
    private Transaction transaction5;

    @BeforeEach
    void setUp() {
        transactionRepository = new TransactionRepository();
        transaction1 = new Transaction(1, 10.0, "cars");
        transaction2 = new Transaction(2, 10.0, "house", 1);
        transaction3 = new Transaction(3, 20.0, "cars");
        transaction4 = new Transaction(4, 30.0, "cars", 1);
        transaction5 = new Transaction(5, 10.0, "cars", 3);
    }

    @Test
    void addShouldSaveGivenTransactionToRepository() {
        transactionRepository.createTransaction(transaction1);
        assertThat(transactionRepository.searchTransactionById(1));
    }

    @Test
    void addShouldSaveGivenTransactionWithParenId(){
        transactionRepository.createTransaction(transaction2);
        assertThat(transactionRepository.searchTransactionById(2));
    }

    @Test
    void getTransactionByIdShouldReturnTransactionByGivenId(){
        transactionRepository.createTransaction(transaction1);
        assertThat(transactionRepository.searchTransactionById(1));
    }

    @Test
    void getSumShouldReturnSumOfAllTransactionAmounts(){
        transactionRepository.createTransaction(transaction1);
        transactionRepository.createTransaction(transaction2);
        transactionRepository.createTransaction(transaction3);
        transactionRepository.createTransaction(transaction4);
        transactionRepository.createTransaction(transaction5);

        assertEquals(50.0, transactionRepository.getTransactionAmount((long)1));
    }

    @Test
    void getTransactionByTypeShouldReturnTransactionIdForGivenType(){
        transactionRepository.createTransaction(transaction1);
        transactionRepository.createTransaction(transaction2);
        transactionRepository.createTransaction(transaction3);
        transactionRepository.createTransaction(transaction4);

        ArrayList<Long> expectedTransactions = new ArrayList<>();
        expectedTransactions.add((long) 1);
        expectedTransactions.add((long) 3);
        expectedTransactions.add((long) 4);

        ArrayList<Long> actualTransactions;
        actualTransactions = transactionRepository.searchTransactionOfType("cars");
        assertEquals(expectedTransactions, actualTransactions);
    }


}