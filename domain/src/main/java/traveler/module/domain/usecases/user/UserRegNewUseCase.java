package traveler.module.domain.usecases.user;

import traveler.module.domain.entity.UserEntity;
import traveler.module.domain.repository.UserDomainRepository;

public class UserRegNewUseCase {

    private final UserDomainRepository repository;

    public UserRegNewUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void regNew(UserEntity newUser, String pass){
        this.repository.regNew(newUser, pass);
    }
}
