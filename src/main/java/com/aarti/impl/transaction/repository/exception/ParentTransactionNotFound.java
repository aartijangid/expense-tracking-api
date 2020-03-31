package com.aarti.impl.transaction.repository.exception;

public class ParentTransactionNotFound extends RuntimeException {
    public ParentTransactionNotFound(){
        super("Invalid Parent Transaction");
    }
}
