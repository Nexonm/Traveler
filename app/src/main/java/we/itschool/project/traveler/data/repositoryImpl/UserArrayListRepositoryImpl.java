package we.itschool.project.traveler.data.repositoryImpl;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.data.NewData;
import we.itschool.project.traveler.domain.entity.User;
import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserArrayListRepositoryImpl implements UserDomainRepository {

    private final MutableLiveData<ArrayList<User>> dataLiveData;
    private final ArrayList<User> data;

    {
        data = new ArrayList<>();
        dataLiveData = new MutableLiveData<>();
    }

    private static int autoIncrementId = 0;

    private static final int NUM_OF_PEOPLE_TO_DO;

    static {
        NUM_OF_PEOPLE_TO_DO = 10;
    }

    {
        for (int i = 0; i < NUM_OF_PEOPLE_TO_DO; i++) {
            userAddNew(
                    NewData.newUser()
            );

        }
    }

    private void updateLiveData() {
        dataLiveData.postValue(new ArrayList<>(data));
        if (AppStart.isLog) {
            Log.w("UserArrayListRepositoryImpl", data.size() + "\n");
        }
    }

    @Override
    public void userAddNew(User user) {
        if (user.get_id() == User.UNDEFINED_ID)
            user.set_id(autoIncrementId++);
        data.add(user);
        updateLiveData();
    }

    @Override
    public void userDeleteById(User user) {
        data.remove(user);
        updateLiveData();
    }

    @Override
    public void userEditById(User user) {
        User user_old = userGetById(user.get_id());
        userDeleteById(user_old);
        userAddNew(user);
    }

    @Override
    public MutableLiveData<ArrayList<User>> userGetAll() {
        return dataLiveData;
    }

    @Override
    public User userGetById(int id) {
        User user = data.get(id);
        if (user != null)
            return user;
        else throw new RuntimeException("There is no element with id = " + id);
    }
}
