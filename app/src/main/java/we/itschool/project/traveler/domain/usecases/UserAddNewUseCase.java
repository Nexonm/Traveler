package we.itschool.project.traveler.domain.usecases;

import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserAddNewUseCase {

    private final UserDomainRepository repository;

    public UserAddNewUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public void userAddNew(String[] data){
        repository.userAddNew(data);
    }
}
