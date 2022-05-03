package we.itschool.project.traveler.data.repositoryImpl;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import we.itschool.project.traveler.data.api.APIServiceConstructor;
import we.itschool.project.traveler.data.api.entityserv.CardServ;
import we.itschool.project.traveler.data.api.mapper.CardEntityMapper;
import we.itschool.project.traveler.data.api.service.APIServiceCard;
import we.itschool.project.traveler.data.api.service.APIServiceStorage;
import we.itschool.project.traveler.data.datamodel.CardModelPOJO;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardArrayListRepositoryImpl implements CardDomainRepository {

    private final MutableLiveData<ArrayList<CardEntity>> dataLiveData;
    private final ArrayList<CardEntity> data;

    {
        data = new ArrayList<>();
        dataLiveData = new MutableLiveData<>();
    }

    private static int autoIncrementId = 0;

    //only for test!!!
    private static int USER_ID_FOR_TEST = 21;

    private static final int NUM_OF_CARDS_TO_DO;

    static {
        NUM_OF_CARDS_TO_DO = 7;
    }

    {
        AsyncTask.execute(() -> {
            Log.v("retrofitLogger", "start getting data from server");
            //sendNewCardToSeverRetrofit(21l);
            for (int i = 6; i <= NUM_OF_CARDS_TO_DO; i++) {
                try {
                    Thread.sleep(100);
                    loadDataRetrofit(i);
//                uploadPhotoToCard(i);
                } catch (InterruptedException e) {
                    loadDataRetrofit(i);
//                uploadPhotoToCard(i);
                }
            }
        });
    }

    public void uploadPhotoToCard(int id) {
        AsyncTask.execute(() -> {

            APIServiceStorage service = APIServiceConstructor.CreateService(APIServiceStorage.class);
            //pass it like this
            File file = new File("src/main/res/drawable-nodpi/a001.png");
            RequestBody requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            Call<String> call = service.uploadPhotoToCard2(body, id);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body() != null) {
                        Log.v("retrofitLogger", "IMAGE_SENT:" + response.body().toString());
                    } else {
                        Log.v("retrofitLogger", "null response.body on IMAGE_SENT");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {

                    Log.v("retrofitLogger", "some error!!! on failure IMAGE_SENT id:" + id +
                            " t:" + t.getMessage());
                }
            });
        });
    }

    private void loadDataRetrofit(int id) {
        AsyncTask.execute(() -> {

            APIServiceCard service = APIServiceConstructor.CreateService(APIServiceCard.class);
            Call<String> call = service.getOneCardById(id);
            //Log.v(MainActivity.TAG, "start catching the answer");
            call.enqueue(new retrofit2.Callback<String>() {
                @Override
                public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                    if (response.body() != null) {
                        String str = response.body().toString();
                        Log.v("retrofitLogger", "card.toString():" + str);
                        cardAddNew(CardEntityMapper.toCardEntityFormCardServ(
                                (new Gson()).fromJson(str, CardServ.class)
                                , true));

                    } else {
                        Log.v("retrofitLogger", "null response.body on loadDataRetrofit");
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<String> call, Throwable t) {
                    Log.v("retrofitLogger", "some error!!! on failure id:" + id +
                            " t:" + t.getMessage());
                }
            });
        });
        // Log.v(MainActivity.TAG, "passed main-response methods");
    }


    private void sendNewCardToSeverRetrofit(long uid) {
        APIServiceCard service = APIServiceConstructor.CreateService(APIServiceCard.class);

        CardModelPOJO model = new CardModelPOJO(
                "some city2",
                "some country2",
                "Big full description for i don't know why2",
                "some extra info available by tap2",
                "Some address2",
                false,
                0,
                true
        );

        String str = (new Gson()).toJson(model);
        Log.v("retrofitLogger", "add card to Server, model.toJson():" + str);
        Call<String> call = service.addNewCardGson(uid, str);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    Log.v("retrofitLogger", "add card to Server, answer" + response.body());
                } else {
                    Log.v("retrofitLogger", "null response.body on sendNewCardToSeverRetrofit");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.v("retrofitLogger", "some error!!! on failure, Cannot add new Card toServer:" +
                        " t:" + t.getMessage());
            }
        });
    }

    private void updateLiveData() {
        AsyncTask.execute(() -> {
            dataLiveData.postValue(new ArrayList<>(data));
        });
    }

    /**
     * Method creates new card. All data represent using CardModel as a POJO
     * @param model of card created by user
     */
    @Override
    public void cardCreateNew(CardModelPOJO model) {
        AsyncTask.execute(() -> {
            APIServiceCard service = APIServiceConstructor.CreateService(APIServiceCard.class);
            Call<String> call = service.addNewCardGson(USER_ID_FOR_TEST, CardEntityMapper.toCardServFromCardModelPOJO(model));
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.body() != null) {
                        Log.v("retrofitLogger", "add card to Server, answer" + response.body());
                    } else {
                        Log.v("retrofitLogger", "null response.body on sendNewCardToSeverRetrofit");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.v("retrofitLogger", "some error!!! on failure, Cannot add new Card toServer:" +
                            " t:" + t.getMessage());
                }
            });
        });
    }

    @Override
    public void cardAddNew(CardEntity card) {
        if (card.get_id() == CardEntity.UNDEFINED_ID)
            card.set_id(autoIncrementId++);
        data.add(card);
        updateLiveData();
    }

    @Override
    public void cardDeleteById(CardEntity card) {
        data.remove(card);
        updateLiveData();
    }

    @Override
    public void cardEditById(CardEntity card) {
        CardEntity card_old = cardGetById(card.get_id());
        cardDeleteById(card_old);
        cardAddNew(card);
    }

    @Override
    public MutableLiveData<ArrayList<CardEntity>> cardGetAll() {
        return dataLiveData;
    }

    @Override
    public CardEntity cardGetById(int id) {
        CardEntity card = data.get(id);
        if (card != null)
            return card;
        else throw new RuntimeException("There is no element with id = " + id);
    }
}
