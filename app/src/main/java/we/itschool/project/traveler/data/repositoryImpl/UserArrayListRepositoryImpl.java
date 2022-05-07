package we.itschool.project.traveler.data.repositoryImpl;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.data.api.APIServiceConstructor;
import we.itschool.project.traveler.data.api.entityserv.UserServ;
import we.itschool.project.traveler.data.api.mapper.UserEntityMapper;
import we.itschool.project.traveler.data.api.service.APIServiceUser;
import we.itschool.project.traveler.domain.entity.UserEntity;
import we.itschool.project.traveler.domain.repository.UserDomainRepository;

public class UserArrayListRepositoryImpl implements UserDomainRepository {

    private final MutableLiveData<ArrayList<UserEntity>> dataLiveData;
    private final ArrayList<UserEntity> data;

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
//            userAddNew(
//                    NewData.newUser()
//            );
        }
//        addNewUserRetrofit();
//        addSomeDataToUser();
    }

    private void addNewUserRetrofit(String email, String password) {
        APIServiceUser service = APIServiceConstructor.CreateService(APIServiceUser.class);

        //email is provided
        String firstName = "some FirstName";
        String secondName = "some SecondName";
        //password is provided
        String dateOfBirth = "20.09.2011";
        JSONObject json = new JSONObject();
        try {
            json.put("password", password);
            json.put("firstName", firstName);
            json.put("secondName", secondName);
            json.put("email", email);
            json.put("dateOfBirth", dateOfBirth);
        } catch (JSONException e) {
            Log.v("retrofitLogger", "some mistake JSONObject" + e.getMessage());
            e.printStackTrace();
        }


        Log.v("retrofitLogger", "add User to Server, model.toJson():" + json.toString());
        Call<String> call = service.regUserMain(json.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.v("retrofitLogger", "add User to Server, answer" + response.body());
                } else {
                    Log.v("retrofitLogger", "null response.body on addNewUserRetrofit");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("retrofitLogger", "some error!!! on failure, Cannot add new User toServer:" +
                        " t:" + t.getMessage());
            }
        });

    }

    private void addSomeDataToUser() {
        APIServiceUser service = APIServiceConstructor.CreateService(APIServiceUser.class);

        String phoneNumber = "some password";
        String socialContacts = "some FirstName";
        boolean isMale = true;
        String email = "someTestEmailFromServer@gmail.com";
        JSONObject json = new JSONObject();
        try {
            json.put("phoneNumber", phoneNumber);
            json.put("socialContacts", socialContacts);
            json.put("isMale", isMale);
            json.put("email", email);
        } catch (JSONException e) {
            Log.v("retrofitLogger", "some mistake JSONObject" + e.getMessage());
            e.printStackTrace();
        }


        Log.v("retrofitLogger", "add User to Server, model.toJson():" + json.toString());
        Call<String> call = service.regUserAdd(json.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.v("retrofitLogger", "additional User info to Server, answer" + response.body());
                } else {
                    Log.v("retrofitLogger", "null response.body on addSomeDataToUser");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("retrofitLogger", "some error!!! on failure, Cannot add new User toServer:" +
                        " t:" + t.getMessage());
            }
        });

    }

    @Override
    public boolean login(String email, String pass) {
        APIServiceUser service = APIServiceConstructor.CreateService(APIServiceUser.class);
        final boolean[] flag = {false};
        Call<String> call = service.loginUser(email, pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    if ("Пароль не соответствует, вход невозможен".equals(response.body().toString())) {
                        flag[0] = false;
                    } else if ("Пользователя не существует".equals(response.body().toString())) {
                        flag[0] = false;
                    } else {
                        UserEntity user = UserEntityMapper.toUserEntityFormUserServ(
                                (new Gson()).fromJson(response.body(), UserServ.class),
                                true
                        );
                        AppStart.setUser(user);
                        flag[0] = true;
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                flag[0] = false;
            }
        });
        return flag[0];
    }


    private void updateLiveData() {
        dataLiveData.postValue(new ArrayList<>(data));
        if (AppStart.isLog) {
            Log.w("UserArrayListRepositoryImpl", data.size() + "\n");
        }
    }

    @Override
    public void userAddNew(String email, String password) {
        addNewUserRetrofit(email, password);
    }

    @Override
    public void userDeleteById(UserEntity user) {
        data.remove(user);
        updateLiveData();
    }

    @Override
    public void userEditById(UserEntity user) {
        UserEntity user_old = userGetById(user.get_id());
        userDeleteById(user_old);
        //TODO when user will be edited make some working logic here
        userAddNew("null", "null");
    }

    @Override
    public MutableLiveData<ArrayList<UserEntity>> userGetAll() {
        return dataLiveData;
    }

    @Override
    public UserEntity userGetById(int id) {
        UserEntity user = data.get(id);
        if (user != null)
            return user;
        else throw new RuntimeException("There is no element with id = " + id);
    }
}
