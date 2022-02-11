package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardDeleteByIdUseCase {

    private final CardDomainRepository repository;

    public CardDeleteByIdUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void cardDeleteById(Card card){
        repository.cardDeleteById(card);
    }

}
