package traveler.module.domain.usecases.user;

import traveler.module.domain.repository.UserDomainRepository;

public class UserSetInterestsUseCase {

    private final UserDomainRepository repository;

    public UserSetInterestsUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void setInterests(String interests){
        this.repository.setInterests(interests);
    }
}
