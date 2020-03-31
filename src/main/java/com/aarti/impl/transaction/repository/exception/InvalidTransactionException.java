package com.aarti.impl.transaction.repository.exception;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(){
        super("Invalid Transaction");
    }
}
