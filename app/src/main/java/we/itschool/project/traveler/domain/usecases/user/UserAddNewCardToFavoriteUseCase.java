package we.itschool.project.traveler.domain.usecases.user;

import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserAddNewCardToFavoriteUseCase {
    private final UserDomainRepository repository;

    public UserAddNewCardToFavoriteUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void userAddNewCardToFavorite(long cid){
        repository.userAddNewCardToFavorite(cid);
    }
}
