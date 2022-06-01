package traveler.module.domain.usecases.card;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.repository.CardDomainRepository;

public class CardGetByIdUseCase {

    private final CardDomainRepository repository;

    public CardGetByIdUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public CardEntity getCardById(long id){
        return repository.getById(id);
    }
}
