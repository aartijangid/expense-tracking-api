package com.aarti.adviqo.transaction.repository.exception;

public class ParentTransactionNotFound extends RuntimeException {
    public ParentTransactionNotFound(){
        super("Invalid Parent Transaction");
    }
}
