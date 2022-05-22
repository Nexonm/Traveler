package we.itschool.project.traveler.domain.usecases.user;

import we.itschool.project.traveler.domain.entity.UserEntity;
import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserDeleteByIdUseCase {

    private final UserDomainRepository repository;

    public UserDeleteByIdUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void cardDeleteById(UserEntity user) {
        repository.userDeleteById(user);
    }

}
