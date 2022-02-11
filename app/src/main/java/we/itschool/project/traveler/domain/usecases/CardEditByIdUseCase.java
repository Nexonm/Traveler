package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardEditByIdUseCase {

    private final CardDomainRepository repository;

    public CardEditByIdUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void cardEditById (Card card){
        repository.cardEditById(card);
    }

}
