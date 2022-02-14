package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardGetByIdUseCase {

    private final CardDomainRepository repository;

    public CardGetByIdUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    Card cardGetById(int id){
        return repository.cardGetById(id);
    }

}
