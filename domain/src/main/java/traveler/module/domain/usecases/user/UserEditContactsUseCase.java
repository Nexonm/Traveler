package traveler.module.domain.usecases.user;

import traveler.module.domain.entity.UserEntity;
import traveler.module.domain.repository.UserDomainRepository;

public class UserEditContactsUseCase {

    private final UserDomainRepository repository;

    public UserEditContactsUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void editContacts(UserEntity entity, String pass){
        this.repository.editContacts(entity, pass);
    }
}
