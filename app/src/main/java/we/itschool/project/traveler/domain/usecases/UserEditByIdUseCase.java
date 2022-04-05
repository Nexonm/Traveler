package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.User;
import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserEditByIdUseCase {

    private final UserDomainRepository repository;

    public UserEditByIdUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void cardEditById(User user) {
        repository.userEditById(user);
    }

}
