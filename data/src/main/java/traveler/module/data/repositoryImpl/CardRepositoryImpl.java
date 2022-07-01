package traveler.module.data.repositoryImpl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import traveler.module.data.travelerapi.entityserv.CardServ;
import traveler.module.data.travelerapi.errors.CardNetAnswers;
import traveler.module.data.travelerapi.mapper.CardEntityMapper;
import traveler.module.data.travelerapi.service.APIServiceCard;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.repository.CardDomainRepository;

public class CardRepositoryImpl implements CardDomainRepository {

    //used to upload data from server id by id
    private static int incrementId;

    private ArrayList<CardEntity> cardsData;

    static{
        incrementId = 0;
    }

    {
        cardsData = new ArrayList<>();
    }

    @Override
    public ArrayList<CardEntity> getAll() {
        return new ArrayList<>(cardsData);
    }

    @Override
    public ArrayList<CardEntity> getBySearch(String searchStr) {
        searchCardsUsingRetrofit(searchStr);
        return cardsData;
    }

    /**
     * uploads cards from server
     */
    @Override
    public void upload() {
        uploadCardByIdUsingRetrofit(++incrementId);
    }

    @Override
    public CardEntity getById(long id) {
        for (CardEntity card : cardsData) {
            if (card.get_id() == id)
                return card;
        }
        return null;
    }

    @Override
    public void edit(CardEntity newCard) {
        //TODO call server to edit card
    }

    /**
     * Upload one card from server by making retrofit request.
     *
     * Note: not always card could be returned. Card could be deleted or doesn't even exist!
     * Other ways - server exceptions.
     *
     * All are checkable.
     * @param id card id to upload
     */
    private void uploadCardByIdUsingRetrofit(long id){
        APIServiceCard service = APIServiceTravelerConstructor.CreateService(APIServiceCard.class);
        Call<String> call = service.getOneCardById(id);

        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.body() != null) {
                    String str = response.body().toString();
                    addCardToData(CardEntityMapper.toCardEntityFormCardServ(
                            (new Gson()).fromJson(str, CardServ.class)
                            , true));

                } else {
                    String errorBody = "wait";
                    try {
                        errorBody = response.errorBody().string();
                    }catch (IOException e){
                    }

                    if (!CardNetAnswers.cardWasDeleted.equals(errorBody))
                        incrementId--;
                    // other messages could be Карты с таким id не существует
                    // or some error
                    //in that case we think that it is the last card in db
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
                    incrementId--;
            }
        });
    }

    private void searchCardsUsingRetrofit(String searchStr){
        APIServiceCard service = APIServiceTravelerConstructor.CreateService(APIServiceCard.class);
        //clear data before full it with new data
        clearCardsData();

        Call<String> call = service.getCardsBySearch(searchStr);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {
                    String str = response.body().toString();
                    ArrayList<Long> list = (new Gson()).fromJson(
                            str,
                            new TypeToken<ArrayList<Long>>() {
                            }.getType()
                    );

                    for (long id : list) {
                        uploadCardByIdUsingRetrofit(id);
                    }

                } else {
                    Log.v("retrofitLogger", "null response.body on loadDataRetrofit");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void addCardToData(CardEntity card){
        cardsData.add(card);
    }

    private void clearCardsData(){
        cardsData.clear();
    }
}
