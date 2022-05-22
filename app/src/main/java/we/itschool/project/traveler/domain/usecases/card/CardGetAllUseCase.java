package we.itschool.project.traveler.domain.usecases.card;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardGetAllUseCase {

    private final CardDomainRepository repository;

    public CardGetAllUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<CardEntity>> cardGetAll(){
        return repository.cardGetAll();
    }

}
