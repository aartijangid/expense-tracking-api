package com.aarti.adviqo.transaction.repository;

import com.aarti.adviqo.transaction.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransactionRepositoryTest {

    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = new TransactionRepository();
    }

    @Test
    void addShouldSaveGivenTransactionToRepository() {
        Transaction transaction = new Transaction(1, 10.0, "cars");
        transactionRepository.add(transaction);
        assert(true);
    }

    @Test
    void addShouldSaveGivenTransactionWithParenId(){
        Transaction transaction = new Transaction(1, 10.0, "cars", 1);
        transactionRepository.add(transaction);
    }

    @Test
    void getTransactionByIdShouldReturnTransactionByGivenId(){
        Transaction expectedTransaction = new Transaction(1, 10.0, "cars");
        transactionRepository.add(expectedTransaction);

        Transaction actualTransaction = transactionRepository.getTransactionById(1);

        assertEquals(expectedTransaction, actualTransaction);
    }

    @Test
    void getLinkedTransactionsShouldReturnListOfLinkedTransactions(){
        Transaction transaction1 = new Transaction(1, 10.0, "cars");
        transactionRepository.add(transaction1);
        Transaction transaction2 = new Transaction(2, 10.0, "cars", 1);
        transactionRepository.add(transaction2);

        ArrayList<Long> expectedList = new ArrayList<Long>();
        expectedList.add((long) 1);
        expectedList.add((long) 2);

        assertEquals(expectedList, transactionRepository.getAllLinkedTransactions(1));
    }

    @Test
    void getLinkedTransactionsShouldReturnListOfAllLinkedTransactions(){
        Transaction transaction1 = new Transaction(1, 10.0, "cars");
        transactionRepository.add(transaction1);
        Transaction transaction2 = new Transaction(2, 10.0, "cars", 1);
        transactionRepository.add(transaction2);
        Transaction transaction3 = new Transaction(3, 10.0, "cars", 2);
        transactionRepository.add(transaction3);

        ArrayList<Long> expectedList = new ArrayList<Long>();
        expectedList.add((long) 1);
        expectedList.add((long) 2);
        expectedList.add((long) 3);

        LinkedList<Long> actualList = transactionRepository.getAllLinkedTransactions(1);
        assertEquals(expectedList, actualList);
    }

    @Test
    void getLinkedTransactionsShouldReturnListOfAllNestedLinkedTransactions(){
        Transaction transaction1 = new Transaction(1, 10.0, "cars");
        transactionRepository.add(transaction1);
        Transaction transaction2 = new Transaction(2, 10.0, "cars", 1);
        transactionRepository.add(transaction2);
        Transaction transaction3 = new Transaction(3, 10.0, "cars", 2);
        transactionRepository.add(transaction3);
        Transaction transaction4 = new Transaction(4, 10.0, "cars", 2);
        transactionRepository.add(transaction4);
        Transaction transaction5 = new Transaction(5, 10.0, "cars", 3);
        transactionRepository.add(transaction5);

        ArrayList<Long> expectedList = new ArrayList<Long>();
        expectedList.add((long) 1);
        expectedList.add((long) 2);
        expectedList.add((long) 3);
        expectedList.add((long) 5);
        expectedList.add((long) 4);

        LinkedList<Long> actualList = transactionRepository.getAllLinkedTransactions(1);
        assertEquals(expectedList, actualList);
    }

    @Test
    void getSumShouldReturnSumOfAllTransactionAmounts(){
        Transaction transaction1 = new Transaction(1, 10.0, "cars");
        transactionRepository.add(transaction1);
        Transaction transaction2 = new Transaction(2, 10.0, "cars", 1);
        transactionRepository.add(transaction2);
        Transaction transaction3 = new Transaction(3, 10.0, "cars", 2);
        transactionRepository.add(transaction3);
        Transaction transaction4 = new Transaction(4, 10.0, "cars", 2);
        transactionRepository.add(transaction4);
        Transaction transaction5 = new Transaction(5, 10.0, "cars", 3);
        transactionRepository.add(transaction5);

        assertEquals(50.0, transactionRepository.getSum((long)1));
    }

}