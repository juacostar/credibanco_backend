package com.credibanco.backin.Service.impl;

import com.credibanco.backin.Service.CardService;
import com.credibanco.backin.dto.*;
import com.credibanco.backin.model.Card;
import com.credibanco.backin.repository.CardRepository;
import com.credibanco.backin.util.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardMapper cardMapper;


    @Override
    public Object createCard(CreateCardRequest request) {

        try{
            Card card = cardMapper.requestToEntity(request);
            Card created = cardRepository.save(card);
            CreateCardResponse createCardResponse = new CreateCardResponse();
            createCardResponse.setCardId(created.getCardid());
            return  createCardResponse;
        }catch(Exception e){
            CreateCardError createCardError = new CreateCardError();
            createCardError.setMessage(e.getMessage());
            return createCardError;
        }

    }

    @Override
    public Object activateCard(ActivateCardRequest activateCardRequest) {

        MessageResponse messageResponse = new MessageResponse();

        try {
            Card card = cardRepository.findCardByCardid(activateCardRequest.getCardId());
            card.setActivated(true);

            cardRepository.save(card);
            messageResponse.setMessage("activated");
            return messageResponse;

        }catch (Exception e){
            messageResponse.setMessage(e.getMessage());
            return messageResponse;
        }
    }

    @Override
    public Object blockCard(String cardId) {
        MessageResponse messageResponse = new MessageResponse();

        try{
            Card card = cardRepository.findCardByCardid(cardId);
            cardRepository.delete(card);
            messageResponse.setMessage("deleted");
            return messageResponse;

        }catch (Exception e){
            messageResponse.setMessage(e.getMessage());
            return messageResponse;
        }
    }

    @Override
    public Object addBalance(AddBalanceRequest addBalanceRequest) {
        MessageResponse messageResponse = new MessageResponse();

        try{
            Card card = cardRepository.findCardByCardid(addBalanceRequest.getCardId());
            if(card.getActivated()) {
                card.setBalance(card.getBalance() + addBalanceRequest.getBalance());
                cardRepository.save(card);
                messageResponse.setMessage("updated");
            }else{
                messageResponse.setMessage("card inactivated");
            }

            return  messageResponse;

        }catch(Exception e){
            messageResponse.setMessage(e.getMessage());
            return messageResponse;
        }

    }

    @Override
    public Object getBalance(String cardId) {

        try{

            GetBalanceCardResponse getBalanceCardResponse = new GetBalanceCardResponse();
            Card card = cardRepository.findCardByCardid(cardId);
            getBalanceCardResponse.setBalance(card.getBalance());

            return  getBalanceCardResponse;

        }catch (Exception e){
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setMessage(e.getMessage());

            return messageResponse;
        }
    }


}
