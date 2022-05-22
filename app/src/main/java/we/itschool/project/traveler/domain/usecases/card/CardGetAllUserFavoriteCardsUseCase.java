package we.itschool.project.traveler.domain.usecases.card;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardGetAllUserFavoriteCardsUseCase {

    CardDomainRepository repository;

    public CardGetAllUserFavoriteCardsUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<CardEntity>> getUserFavCards(){
        return repository.getUserFavCards();
    }
}
