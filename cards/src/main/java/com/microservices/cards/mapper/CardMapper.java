package com.microservices.cards.mapper;

import com.microservices.cards.dto.CardDto;
import com.microservices.cards.entity.Card;

public class CardMapper {

  public static CardDto mapToCardsDto(Card cards, CardDto cardsDto) {
    cardsDto.setCardNumber(cards.getCardNumber());
    cardsDto.setCardType(cards.getCardType());
    cardsDto.setMobileNumber(cards.getMobileNumber());
    cardsDto.setTotalLimit(cards.getTotalLimit());
    cardsDto.setAvailableAmount(cards.getAvailableAmount());
    cardsDto.setAmountUsed(cards.getAmountUsed());
    return cardsDto;
  }

  public static Card mapToCards(CardDto cardsDto, Card cards) {
    cards.setCardNumber(cardsDto.getCardNumber());
    cards.setCardType(cardsDto.getCardType());
    cards.setMobileNumber(cardsDto.getMobileNumber());
    cards.setTotalLimit(cardsDto.getTotalLimit());
    cards.setAvailableAmount(cardsDto.getAvailableAmount());
    cards.setAmountUsed(cardsDto.getAmountUsed());
    return cards;
  }

}