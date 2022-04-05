package we.itschool.project.traveler.domain.usecases;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.User;
import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserGetAllUseCase {

    private final UserDomainRepository repository;

    public UserGetAllUseCase(UserDomainRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<User>> userGetAll() {
        return repository.userGetAll();
    }

}
