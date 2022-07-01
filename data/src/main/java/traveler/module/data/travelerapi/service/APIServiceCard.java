package traveler.module.data.travelerapi.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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
     * Calls server to delete card that user made. Need user email and pass to check,
     * if user is correct and card can be deleted.
     * @param cid card cid
     * @param email user email
     * @param pass user password
     * @return gson str from CardModel
     */
    @HTTP(method = "DELETE", path = "cards/delete-by-id", hasBody = true)
    Call<String> deleteCardById(
            @Query(value = "cid") long cid,
            @Query(value = "uemail") String email,
            @Body String pass
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
