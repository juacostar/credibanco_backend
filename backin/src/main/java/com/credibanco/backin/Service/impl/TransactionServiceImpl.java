package com.credibanco.backin.Service.impl;

import antlr.debug.MessageAdapter;
import com.credibanco.backin.Service.TransactionService;
import com.credibanco.backin.dto.*;
import com.credibanco.backin.model.Card;
import com.credibanco.backin.model.Transaction;
import com.credibanco.backin.repository.CardRepository;
import com.credibanco.backin.repository.TransactionRepository;
import com.credibanco.backin.util.TransactionMapper;
import com.credibanco.backin.util.ValidateTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private ValidateTransaction validateTransaction;

    @Override
    public Object createTransaction(CreateTransactionRequest createTransactionRequest) {

        try{



            Card card = cardRepository.findCardByCardid(createTransactionRequest.getCardId());
            if(card.getBalance() < createTransactionRequest.getPrice()){
                throw new Exception("not enough balance");
            }

            Transaction transaction = transactionMapper.requestToEntity(createTransactionRequest, card);


            Transaction saved = transactionRepository.save(transaction);

            CreateTransactionResponse createTransactionResponse = new CreateTransactionResponse();
            createTransactionResponse.setTransactionId(saved.getTransactionId());


            card.setBalance(card.getBalance() - transaction.getPrice());
            cardRepository.save(card);

            return createTransactionResponse;
        }catch (Exception e){

            MessageResponse messageResponse = new MessageResponse();

            messageResponse.setMessage(e.getMessage());
            return  messageResponse;

        }
    }

    @Override
    public Object getTransaction(String transactionId) {

        try {

            Transaction transaction = transactionRepository.findTransactionByTransactionId(transactionId);

            GetTransactionResponse getTransactionResponse = new GetTransactionResponse();
            getTransactionResponse.setCardId(transaction.getCard().getCardid());
            getTransactionResponse.setPrice(transaction.getPrice());
            getTransactionResponse.setTransactionId(transaction.getTransactionId());

            return  getTransactionResponse;

        }catch (Exception e){

            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage(e.getMessage());
            return messageResponse;

        }

    }

    @Override
    public Object anulateTransaction(AnulateTransactionRequest anulateTransactionRequest) {

        MessageResponse messageResponse = new MessageResponse();
        try{
            Card card = cardRepository.findCardByCardid(anulateTransactionRequest.getCardId());
            Transaction transaction = transactionRepository.findTransactionByTransactionId(anulateTransactionRequest.getTransactionId());
            if(validateTransaction.validateNullTransaction(transaction)){
                transaction.setAnulled(true);
                transactionRepository.save(transaction);
            }
            else{

                messageResponse.setMessage("Transaction exceed 24 hours");
                return messageResponse;
            }

            card.setBalance(card.getBalance() + transaction.getPrice());
            cardRepository.save(card);
            messageResponse.setMessage("anulled");
            return  messageResponse;


        }catch(Exception e){
            messageResponse.setMessage(e.getMessage());
            return messageResponse;

        }

    }
}
