package we.itschool.project.traveler.domain.usecases.card;

import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardDeleteByIdUseCase {

    private final CardDomainRepository repository;

    public CardDeleteByIdUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void cardDeleteById(CardEntity card){
        repository.cardDeleteById(card);
    }

}
