package traveler.module.data.repositoryImpl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
import traveler.module.data.travelerapi.entityserv.CardServ;
import traveler.module.data.travelerapi.entityserv.UserServ;
import traveler.module.data.travelerapi.mapper.CardEntityMapper;
import traveler.module.data.travelerapi.mapper.UserEntityMapper;
import traveler.module.data.travelerapi.service.APIServiceCard;
import traveler.module.data.travelerapi.service.APIServiceStorage;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.travelerapi.service.APIServiceUser;
import traveler.module.data.usecasesimplementation.LoginUCI;
import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.entity.UserEntity;
import traveler.module.domain.repository.UserDomainRepository;

public class UserRepositoryImpl implements UserDomainRepository {

    private static UserEntity userMain;
    private static ArrayList<CardEntity> userFavCards;

    static {
        userFavCards = new ArrayList<>();
    }


    @Override
    public UserEntity getMainUserForThisApp() {
        return userMain;
    }

    public static UserEntity getUserMain(){
        return userMain;
    }

    public static void setUserMain(UserEntity user){
        userMain = user;
    }

    @Override
    public String login(String pass, String login) {
        LoginUCI loginUCI = new LoginUCI();
        return loginUCI.login(pass, login);
    }

    @Override
    public ArrayList<CardEntity> getUserCards() {
        return (ArrayList<CardEntity>) (userMain.getUserInfo().getUserCards());
    }

    @Override
    public ArrayList<CardEntity> getUserFavoritesCards(long id) {
        return userFavCards;
    }

    @Override
    public void addCardToFavorites(long cid) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);

        Call<String> call = service.addCardIdToUserFavs(userMain.get_id(), cid);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    ArrayList<Long> list = (new Gson()).fromJson(
                            response.body(),
                            new TypeToken<ArrayList<Long>>() {
                            }.getType()
                    );
                    userMain.getUserInfo().setUserFavoritesCards(list);
//                    Log.v("retrofitLogger", "add FAVS, answer:" + response.body() +
//                            ", user's:" + AppStart.getUser().getUserInfo().getUserFavoritesCards());
                    chekUserFavs(list);
                } else {
//                    Log.v("retrofitLogger", "null response.body on addNewUserRetrofit");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void createNewCard(CardEntity createdCard) {
        APIServiceCard service = APIServiceTravelerConstructor.CreateService(APIServiceCard.class);
        Call<String> call = service.addNewCardGson(userMain.get_id(), CardEntityMapper.toCardServFromCardEntity(createdCard));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
//                    Log.v("retrofitLogger", "card.toString():" + response.body().toString());
                    CardServ cardServ = (new Gson()).fromJson(response.body().toString(), CardServ.class);
                    int cid = cardServ.getId();
                    uploadPhotoToCard(createdCard.getCardInfo().getPathToPhoto(), cid);

                } else {
//                    Log.v("retrofitLogger", "null response.body on sendNewCardToSeverRetrofit");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Log.v("retrofitLogger", "some error!!! on failure, Cannot add new Card toServer:" +
//                        " t:" + t.getMessage());
            }
        });
    }

    /**
     * Sends photo to created card as a resource.
     * Then sends http request to add new card to user cards for MyCardsFragment
     *
     * @param path path to photo in user app
     * @param cid  card id
     */
    private void uploadPhotoToCard(String path, long cid) {
        APIServiceStorage service = APIServiceTravelerConstructor.CreateService(APIServiceStorage.class);
        //pass it like this
//            Log.v("retrofitLogger", "get file from image");
        File file = new File(path);
//            Log.v("retrofitLogger", "fill it in request Body");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

//          MultipartBody.Part is used to send also the actual file name
//            Log.v("retrofitLogger", "fill it in multipart Body");
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//            Log.v("retrofitLogger", "Make call");
        RequestBody cidBody = RequestBody.create(MediaType.parse("multipart/from-data"), String.valueOf(cid));
        Call<String> call = service.uploadPhotoToCard2(body, cidBody);
//            Log.v("retrofitLogger", "trying to send data");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.v("retrofitLogger", "IMAGE_SENT:" + response.body().toString());
                    //add new card to users account
                    loadCardToUser(cid);

                } else {
                    Log.v("retrofitLogger", "null response.body on IMAGE_SENT");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                Log.v("retrofitLogger", "some error!!! on failure IMAGE_SENT id:" + cid +
                        " t:" + t.getMessage());
            }
        });
    }

    /**
     * loads new card from server for user cards
     *
     * @param id card id
     */
    private void loadCardToUser(long id) {
//        AsyncTask.execute(() -> {

        APIServiceCard service = APIServiceTravelerConstructor.CreateService(APIServiceCard.class);
        Call<String> call = service.getOneCardById(id);
        //Log.v(MainActivity.TAG, "start catching the answer");
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.body() != null) {
                    String str = response.body().toString();
//                    Log.v("retrofitLogger", "card.toString():" + str);
                    //add card to userMain card list
                    addCardToUserCards(CardEntityMapper.toCardEntityFormCardServ(
                            (new Gson()).fromJson(str, CardServ.class)
                            , true));

                } else {
//                    Log.v("retrofitLogger", "null response.body on loadDataRetrofit");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
//                Log.v("retrofitLogger", "some error!!! on failure id:" + id +
//                        " t:" + t.getMessage());
            }
        });
//        });
    }

    @Override
    public void addPhoto(String path) {
//as it called from ui thread make asynk
        //edit: this layer doesn't know about the ui
//        AsyncTask.execute(() -> {

        APIServiceStorage service = APIServiceTravelerConstructor.CreateService(APIServiceStorage.class);
        //pass it like this
//        Log.v("retrofitLogger", "get file, pathToPhoto is: " + path);
        File file = new File(path);
//            Log.v("retrofitLogger", "fill it in request Body");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

//          MultipartBody.Part is used to send also the actual file name
//            Log.v("retrofitLogger", "fill it in multipart Body");
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//            Log.v("retrofitLogger", "Make call");
        RequestBody cidBody = RequestBody.create(MediaType.parse("multipart/from-data"), String.valueOf(userMain.get_id()));
        Call<String> call = service.uploadPhotoToUser(body, cidBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
//                    Log.v("retrofitLogger", "IMAGE_SENT:" + response.body().toString());
                    UserEntity user = UserEntityMapper.toUserEntityFormUserServ(
                            (new Gson()).fromJson(response.body(), UserServ.class),
                            true
                    );
                    userMain.getUserInfo().setPathToPhoto(user.getUserInfo().getPathToPhoto());

                } else {
//                    Log.v("retrofitLogger", "null response.body on IMAGE_SENT");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Log.v("retrofitLogger", "some error!!! on failure IMAGE_SENT id:" + userMain.get_id() +
//                        " t:" + t.getMessage());
            }
        });
//        });
    }

    @Override
    public void regNew(UserEntity newUser, String pass) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);

        JSONObject json = new JSONObject();
        try {
            json.put("email", newUser.getUserInfo().getEmail());
            json.put("password", pass);
            json.put("firstName", newUser.getUserInfo().getFirstName());
            json.put("secondName", newUser.getUserInfo().getSecondName());
            json.put("dateOfBirth", newUser.getUserInfo().getDateOfBirth());
            json.put("phoneNumber", newUser.getUserInfo().getPhoneNumber());
            json.put("isMale", newUser.getUserInfo().isMale());
            json.put("socialContacts", newUser.getUserInfo().getSocialContacts());
        } catch (JSONException e) {
//            Log.v("retrofitLogger", "some mistake JSONObject" + e.getMessage());
            e.printStackTrace();
        }


//        Log.v("retrofitLogger", "add User to Server, model.toJson():" + json.toString());
        Call<String> call = service.regUserMain(json.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    userMain = UserEntityMapper.toUserEntityFormUserServ(
                            (new Gson()).fromJson(response.body().toString(), UserServ.class),
                            true
                    );
//                    Log.v("retrofitLogger", "add User to Server, answer" + response.body());
                } else {
//                    Log.v("retrofitLogger", "null response.body on addNewUserRetrofit");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Log.v("retrofitLogger", "some error!!! on failure, Cannot add new User toServer:" +
//                        " t:" + t.getMessage());
            }
        });
    }

    @Override
    public void setInterests(String interests) {
        //TODO send server request to set Interests
    }

    @Override
    public void setDescription(String desc) {
        //TODO send server request to ser Description
    }

    private void addCardToUserCards(CardEntity card) {
        userMain.getUserInfo().getUserCards().add(card);
    }

    private void chekUserFavs(ArrayList<Long> list) {
        uploadCardToFavs(
                list.get(
                        list.size() - 1
                )
        );
    }

    private void uploadCardToFavs(long cid) {
        APIServiceCard service = APIServiceTravelerConstructor.CreateService(APIServiceCard.class);
        Call<String> call = service.getOneCardById(cid);
        //Log.v(MainActivity.TAG, "start catching the answer");
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.body() != null) {
                    String str = response.body().toString();
//                    Log.v("retrofitLogger", "card.toString():" + str);
                    //add card to userMain card list
                    addCardToUserFavsData(CardEntityMapper.toCardEntityFormCardServ(
                            (new Gson()).fromJson(str, CardServ.class)
                            , true));

                } else {
//                    Log.v("retrofitLogger", "null response.body on loadDataRetrofit");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
//                Log.v("retrofitLogger", "some error!!! on failure id:" + id +
//                        " t:" + t.getMessage());
            }
        });
    }

    private void addCardToUserFavsData(CardEntity card) {
        userFavCards.add(card);
    }
}
