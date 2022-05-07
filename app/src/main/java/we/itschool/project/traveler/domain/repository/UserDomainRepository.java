package we.itschool.project.traveler.domain.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.UserEntity;

public interface UserDomainRepository {

    boolean login(String email, String pass);

    void userAddNew(String email, String pass);

    void userDeleteById(UserEntity user);

    void userEditById(UserEntity user);

    MutableLiveData<ArrayList<UserEntity>> userGetAll();

    UserEntity userGetById(int id);

}
