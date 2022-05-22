package we.itschool.project.traveler.domain.usecases.user;

import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserLoginUseCase {
    private final UserDomainRepository repository;

    public UserLoginUseCase(UserDomainRepository repository)   {
        this.repository = repository;
    }

    public boolean login(String email, String pass) {
        return repository.login(email, pass);
    }
}
