package com.credibanco.backin.Service;

import com.credibanco.backin.dto.ActivateCardRequest;
import com.credibanco.backin.dto.AddBalanceRequest;
import com.credibanco.backin.dto.CreateCardRequest;
import com.credibanco.backin.model.Card;

public interface CardService {

    Object createCard(CreateCardRequest request);

    Object activateCard(ActivateCardRequest activateCardRequest);

    Object blockCard(String cardId);

    Object addBalance(AddBalanceRequest addBalanceRequest);

    Object getBalance(String cardId);
}
