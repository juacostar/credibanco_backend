package com.credibanco.backin.repository;

import com.credibanco.backin.model.Card;
import com.credibanco.backin.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    Transaction findTransactionByTransactionId(String transactionId);


}
