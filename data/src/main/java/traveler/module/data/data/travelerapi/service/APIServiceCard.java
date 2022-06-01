package traveler.module.data.data.travelerapi.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIServiceCard {

    @GET("cards/get/")
    Call<String> getOneCardById(
            @Query("id") long id
    );

    @POST("cards/add/gson")
    Call<String> addNewCardGson(
            @Query("uid") long id,
            @Body String gsonStr
    );

    /**
     * Calls server and tries to find cards, where
     * hashtag, City, Country fields have such word
     * @param str key word how we can find cards
     * @return gson str from ArrayList
     */
    @POST("cards/get-by-str")
    Call<String> getCardsBySearch(
            @Body String str
    );
}
