package we.itschool.project.traveler.domain.usecases.user;

import we.itschool.project.traveler.domain.entity.UserEntity;
import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserGetByIdUseCase {

    private final UserDomainRepository repository;

    public UserGetByIdUseCase(UserDomainRepository repository)   {
        this.repository = repository;
    }

    UserEntity userGetById(int id) {
        return repository.userGetById(id);
    }

}
