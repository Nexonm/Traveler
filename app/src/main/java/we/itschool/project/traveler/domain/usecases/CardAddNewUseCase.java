package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardAddNewUseCase {

    private final CardDomainRepository repository;

    public CardAddNewUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void cardAddNew(Card card){
        repository.cardAddNew(card);
    }
}
