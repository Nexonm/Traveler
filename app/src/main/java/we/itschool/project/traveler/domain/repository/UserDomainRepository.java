package we.itschool.project.traveler.domain.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.UserEntity;

public interface UserDomainRepository {

    void userAddNew(UserEntity user);

    void userDeleteById(UserEntity user);

    void userEditById(UserEntity user);

    MutableLiveData<ArrayList<UserEntity>> userGetAll();

    UserEntity userGetById(int id);

}
