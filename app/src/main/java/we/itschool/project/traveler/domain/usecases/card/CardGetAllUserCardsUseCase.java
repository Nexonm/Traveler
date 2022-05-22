package we.itschool.project.traveler.domain.usecases.card;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardGetAllUserCardsUseCase {
    private final CardDomainRepository repository;

    public CardGetAllUserCardsUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<CardEntity>> getUserCards() {
        return repository.getUserCards();
    }
}
