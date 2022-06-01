package traveler.module.mapapi.opentripmapapi.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import traveler.module.mapapi.opentripmapapi.ResponseOTM.ResponseOTM;

public interface APIServiceOTMGetPlaces {

    @GET("0.1/{lang}/places/radius")
    Call<ResponseOTM> getPlaces(
            @Path(value = "lang", encoded = true) String lang,
            @Query("radius") int radius,
            @Query("lon") Double lon,
            @Query("lat") Double lat,
            @Query("kinds") String kinds,
            @Query("apikey") String key
    );

}
