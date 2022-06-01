package traveler.module.domain.usecases.card;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.repository.CardDomainRepository;

public class CardEditUseCase {

    private final CardDomainRepository repository;

    public CardEditUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void edit(CardEntity newCard){
        this.repository.edit(newCard);
    }
}
