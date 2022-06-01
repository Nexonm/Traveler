package traveler.module.domain.usecases.card;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.repository.CardDomainRepository;

public class CardGetAllUseCase {

    private final CardDomainRepository repository;

    public CardGetAllUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public ArrayList<CardEntity> getAll(){
        return repository.getAll();
    }
}
