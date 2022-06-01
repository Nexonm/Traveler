package traveler.module.domain.usecases.card;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.repository.CardDomainRepository;

public class CardGetBySearchUseCase {

    private final CardDomainRepository repository;

    public CardGetBySearchUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public ArrayList<CardEntity> getBySearch(String searchStr){
        return this.repository.getBySearch(searchStr);
    }
}
