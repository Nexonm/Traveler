package traveler.module.domain.usecases.user;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.repository.UserDomainRepository;

public class UserGetUserCardsUseCase {

    private final UserDomainRepository repository;

    public UserGetUserCardsUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public ArrayList<CardEntity> getUserCards(){
        return this.repository.getUserCards();
    }
}
