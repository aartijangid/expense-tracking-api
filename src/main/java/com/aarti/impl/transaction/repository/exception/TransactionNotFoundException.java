package com.aarti.impl.transaction.repository.exception;

public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(){
        super("Transaction not found");
    }
}
