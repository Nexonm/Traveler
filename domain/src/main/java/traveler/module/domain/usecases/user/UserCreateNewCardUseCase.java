package traveler.module.domain.usecases.user;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.repository.UserDomainRepository;

public class UserCreateNewCardUseCase {

    private final UserDomainRepository repository;

    public UserCreateNewCardUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void createNewCard(CardEntity newCard){
        this.repository.createNewCard(newCard);
    }
}
