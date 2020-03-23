package com.aarti.adviqo.transaction.usecases.get.byType;

import java.util.ArrayList;

public interface GetTransactionByType {
    ArrayList<Long> searchTransactionOfType(String type);
}
