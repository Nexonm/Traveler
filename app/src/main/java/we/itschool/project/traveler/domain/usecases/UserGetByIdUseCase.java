package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.entity.User;
import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserGetByIdUseCase {

    private final UserDomainRepository repository;

    public UserGetByIdUseCase(UserDomainRepository repository)   {
        this.repository = repository;
    }

    User userGetById(int id) {
        return repository.userGetById(id);
    }

}
