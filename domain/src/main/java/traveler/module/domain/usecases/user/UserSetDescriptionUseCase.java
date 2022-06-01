package traveler.module.domain.usecases.user;

import traveler.module.domain.repository.UserDomainRepository;

public class UserSetDescriptionUseCase {

    private final UserDomainRepository repository;

    public UserSetDescriptionUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void setDescription(String desc){
        this.repository.setDescription(desc);
    }
}
