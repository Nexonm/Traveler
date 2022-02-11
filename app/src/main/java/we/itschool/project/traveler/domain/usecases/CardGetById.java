package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardGetById {

    private final CardDomainRepository repository;

    public CardGetById(CardDomainRepository repository) {
        this.repository = repository;
    }

    Card cardGetById(int id){
        return repository.cardGetById(id);
    }

}
