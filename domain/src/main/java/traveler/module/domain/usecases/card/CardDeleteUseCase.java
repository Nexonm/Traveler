package traveler.module.domain.usecases.card;

import traveler.module.domain.repository.CardDomainRepository;

public class CardDeleteUseCase {

    private final CardDomainRepository repository;

    public CardDeleteUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void delete(long id){
        this.repository.delete(id);
    }
}