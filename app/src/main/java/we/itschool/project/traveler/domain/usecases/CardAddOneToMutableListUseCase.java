package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardAddOneToMutableListUseCase {

    private final CardDomainRepository repository;

    public CardAddOneToMutableListUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    public void cardAddUserCardToMutableList(CardEntity entity) {
        repository.cardAddUserCardToMutableList(entity);
    }
}
