package we.itschool.project.traveler.domain.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.User;

public interface UserDomainRepository {

    void userAddNew(User user);

    void userDeleteById(User user);

    void userEditById(User user);

    MutableLiveData<ArrayList<User>> userGetAll();

    User userGetById(int id);

}
