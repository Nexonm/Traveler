package traveler.module.domain.usecases.user;

import traveler.module.domain.repository.UserDomainRepository;

public class UserAddCardToFavoritesUseCase {

    private final UserDomainRepository repository;

    public UserAddCardToFavoritesUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void addCardToFavorites(long id){
        this.repository.addCardToFavorites(id);
    }
}
