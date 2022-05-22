package we.itschool.project.traveler.domain.usecases.card;

import we.itschool.project.traveler.data.datamodel.CardModelPOJO;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardCreateNewUseCase {
    private final CardDomainRepository repository;

    public CardCreateNewUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void cardCreateNew(CardModelPOJO model){
        repository.cardCreateNew(model);
    }
}
