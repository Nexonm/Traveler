package traveler.module.domain.usecases.user;

import traveler.module.domain.entity.UserEntity;
import traveler.module.domain.repository.UserDomainRepository;

public class UserGetMainUseCase {

    private final UserDomainRepository repository;

    public UserGetMainUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public UserEntity getMainUser(){
        return repository.getMainUserForThisApp();
    }
}
