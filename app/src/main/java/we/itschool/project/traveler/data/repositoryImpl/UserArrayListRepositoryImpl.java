package we.itschool.project.traveler.data.repositoryImpl;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.data.api.travelerapi.entityserv.UserServ;
import we.itschool.project.traveler.data.api.travelerapi.mapper.UserEntityMapper;
import we.itschool.project.traveler.data.api.travelerapi.service.APIServiceStorage;
import we.itschool.project.traveler.data.api.travelerapi.service.APIServiceTravelerConstructor;
import we.itschool.project.traveler.data.api.travelerapi.service.APIServiceUser;
import we.itschool.project.traveler.domain.entity.CardEntity;
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

    @Override
    public void userAddNewCardToFavorite(long cid) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);

        Call<String> call = service.addCardIdToUserFavs(AppStart.getUser().get_id(), cid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    ArrayList<Long> list = (new Gson()).fromJson(response.body(), ArrayList.class);
                    AppStart.getUser().getUserInfo().setUserFavoritesCards(list);
                    Log.v("retrofitLogger", "add FAVS, answer:" + response.body() +
                            ", user's:"+ AppStart.getUser().getUserInfo().getUserFavoritesCards());
                } else {
                    Log.v("retrofitLogger", "null response.body on addNewUserRetrofit");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void addPhotoToUser(String pathToPhoto) {
        //as it called from ui thread make asynk
        AsyncTask.execute(() -> {

            APIServiceStorage service = APIServiceTravelerConstructor.CreateService(APIServiceStorage.class);
            //pass it like this
            Log.v("retrofitLogger", "get file, pathToPhoto is: " + pathToPhoto);
            File file = new File(pathToPhoto);
//            Log.v("retrofitLogger", "fill it in request Body");
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

//          MultipartBody.Part is used to send also the actual file name
//            Log.v("retrofitLogger", "fill it in multipart Body");
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//            Log.v("retrofitLogger", "Make call");
            RequestBody cidBody = RequestBody.create(MediaType.parse("multipart/from-data"), String.valueOf(AppStart.getUser().get_id()));
            Call<String> call = service.uploadPhotoToUser(body, cidBody);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body() != null) {
                        Log.v("retrofitLogger", "IMAGE_SENT:" + response.body().toString());
                        UserEntity user = UserEntityMapper.toUserEntityFormUserServ(
                                (new Gson()).fromJson(response.body(), UserServ.class),
                                true
                        );
                        AppStart.getUser().getUserInfo().setPathToPhoto(user.getUserInfo().getPathToPhoto());

                    } else {
                        Log.v("retrofitLogger", "null response.body on IMAGE_SENT");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.v("retrofitLogger", "some error!!! on failure IMAGE_SENT id:" + AppStart.getUser().get_id() +
                            " t:" + t.getMessage());
                }
            });
        });
    }

    /**
     * Sends json str to server in attempt to register new user
     *
     * @param data email, password, firstName, secondName, dateOfBirth, phoneNumber
     */
    private void addNewUserRetrofit(String[] data) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);

        JSONObject json = new JSONObject();
        try {
            json.put("email", data[0]);
            json.put("password", data[1]);
            json.put("firstName", data[2]);
            json.put("secondName", data[3]);
            json.put("dateOfBirth", data[4]);
            json.put("phoneNumber", data[5]);
            json.put("male", data[6]);
            json.put("socialContacts", data[7]);
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
                    UserEntity user = UserEntityMapper.toUserEntityFormUserServ(
                            (new Gson()).fromJson(response.body().toString(), UserServ.class),
                            true
                    );
                    AppStart.setUser(user);
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

    /**
     * Sends data to server as email and pass. Server sends UserEntity if user exists
     *
     * @param email email or login (actually same)
     * @param pass  password
     *
     * @return true, if user exists
     */
    @Override
    public boolean login(String email, String pass) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);
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
                        //add user cards to mutable data
                        for (CardEntity card : AppStart.getUser().getUserInfo().getUserCards()) {
                            AppStart.imp.addCardToMutableDataUserCards(card);
                        }
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
//        return flag;
    }


    private void updateLiveData() {
        dataLiveData.postValue(new ArrayList<>(data));
        if (AppStart.isLog) {
            Log.w("UserArrayListRepositoryImpl", data.size() + "\n");
        }
    }

    @Override
    public void userAddNew(String[] data) {
        addNewUserRetrofit(data);
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
