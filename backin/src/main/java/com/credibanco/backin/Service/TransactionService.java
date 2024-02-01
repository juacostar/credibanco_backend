package com.credibanco.backin.Service;

import com.credibanco.backin.dto.AnulateTransactionRequest;
import com.credibanco.backin.dto.CreateTransactionRequest;

public interface TransactionService {

    Object createTransaction(CreateTransactionRequest createTransactionRequest);

    Object getTransaction(String transactionId);

    Object anulateTransaction(AnulateTransactionRequest anulateTransactionRequest);

}
