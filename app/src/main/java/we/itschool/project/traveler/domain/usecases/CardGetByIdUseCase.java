package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardGetByIdUseCase {

    private final CardDomainRepository repository;

    public CardGetByIdUseCase(CardDomainRepository repository) {
        this.repository = repository;
    }

    CardEntity cardGetById(int id){
        return repository.cardGetById(id);
    }

}
