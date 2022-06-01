package traveler.module.domain.usecases.user;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.repository.UserDomainRepository;

public class UserGetUserFavoritesCardsUseCase {

    private final UserDomainRepository repository;

    public UserGetUserFavoritesCardsUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public ArrayList<CardEntity> getUserFavoritesCards(long id) {
        return this.repository.getUserFavoritesCards(id);
    }
}
