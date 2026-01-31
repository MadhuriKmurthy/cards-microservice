package com.microservices.cards.service;

import com.microservices.cards.dto.CardDto;
import com.microservices.cards.entity.Card;
import com.microservices.cards.exception.CardAlreadyExistsException;
import com.microservices.cards.exception.ResourceNotFoundException;
import com.microservices.cards.mapper.CardMapper;
import com.microservices.cards.repository.CardRepository;
import com.microservices.cards.util.CardsConstants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardService implements ICardService {

  private CardRepository cardsRepository;

  /**
   * @param mobileNumber - Mobile Number of the Customer
   */
  @Override
  public void createCard(String mobileNumber) {
    Optional<Card> optionalCards= cardsRepository.findByMobileNumber(mobileNumber);
    if(optionalCards.isPresent()){
      throw new CardAlreadyExistsException("Card already registered with given mobileNumber "+mobileNumber);
    }
    cardsRepository.save(createNewCard(mobileNumber));
  }

  /**
   * @param mobileNumber - Mobile Number of the Customer
   * @return the new card details
   */
  private Card createNewCard(String mobileNumber) {
    Card newCard = new Card();
    long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
    newCard.setCardNumber(Long.toString(randomCardNumber));
    newCard.setMobileNumber(mobileNumber);
    newCard.setCardType(CardsConstants.CREDIT_CARD);
    newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
    newCard.setAmountUsed(0);
    newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
    return newCard;
  }

  /**
   *
   * @param mobileNumber - Input mobile Number
   * @return Card Details based on a given mobileNumber
   */
  @Override
  public CardDto fetchCard(String mobileNumber) {
    Card cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
    );
    return CardMapper.mapToCardsDto(cards, new CardDto());
  }

  /**
   *
   * @param cardsDto - CardsDto Object
   * @return boolean indicating if the update of card details is successful or not
   */
  @Override
  public boolean updateCard(CardDto cardsDto) {
    Card cards = cardsRepository.findByCardNumber(cardsDto.getCardNumber()).orElseThrow(
            () -> new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
    CardMapper.mapToCards(cardsDto, cards);
    cardsRepository.save(cards);
    return  true;
  }

  /**
   * @param mobileNumber - Input MobileNumber
   * @return boolean indicating if the delete of card details is successful or not
   */
  @Override
  public boolean deleteCard(String mobileNumber) {
    Card cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
            () -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber)
    );
    cardsRepository.deleteById(cards.getCardId());
    return true;
  }


}