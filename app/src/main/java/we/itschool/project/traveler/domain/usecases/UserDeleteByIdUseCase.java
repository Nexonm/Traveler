package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.User;
import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserDeleteByIdUseCase {

    private final UserDomainRepository repository;

    public UserDeleteByIdUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void cardDeleteById(User user) {
        repository.userDeleteById(user);
    }

}
