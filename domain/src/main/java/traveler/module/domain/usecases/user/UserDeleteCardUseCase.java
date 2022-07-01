package traveler.module.domain.usecases.user;

import traveler.module.domain.repository.UserDomainRepository;

public class UserDeleteCardUseCase {

    private final UserDomainRepository repository;

    public UserDeleteCardUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void deleteCard(long id, String uemail, String pass){
        this.repository.deleteCard(id, uemail, pass);
    }
}