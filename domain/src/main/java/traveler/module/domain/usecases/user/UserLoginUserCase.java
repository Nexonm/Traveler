package traveler.module.domain.usecases.user;

import traveler.module.domain.repository.UserDomainRepository;

public class UserLoginUserCase {

    private final UserDomainRepository repository;

    public UserLoginUserCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void login(String pass, String login){
        this.repository.login(pass, login);
    }
}
