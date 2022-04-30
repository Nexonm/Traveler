package we.itschool.project.traveler.data.api.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIServiceCard {

    @GET("cards/get/")
    Call<String> getOneCardById(
            @Query("id") int id
    );

    @POST("cards/add/gson")
    Call<String> addNewCardGson(
            @Query("uid") long id,
            @Body String gsonStr
    );
}
