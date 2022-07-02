package traveler.module.data.travelerapi.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface APIServiceUser {

    /**
     * Main registration method. It is first to register NEW user.
     * It is used to send first data to server.
     * Saves only 5 main fields, as:
     * <ul>
     *     <li>String: email</li>
     *     <li>String: password</li>
     *     <li>String: firstName</li>
     *     <li>String: secondName</li>
     *     <li>long: dateOfBirth (required check if user isn't under 18)</li>
     * </ul>
     * @param gsonStr str with 5 main fields
     * @return String answer from server
     */
    @POST("people/reg-main")
    public Call<String> regUserMain(
            @Body String gsonStr
    );

    /**
     * Registration additional method. It is second to register new user.
     * It is used to send second-additional data to server.
     * Sends new fields such as:
     * <ul>
     *     <li>String: phoneNumber</li>
     *     <li>String: socialContacts</li>
     *     <li>boolean: isMale</li>
     *     <li>String: secondName</li>
     * </ul>
     *
     * @param gsonStr String with 3 extra fields
     * @return String answer from server
     */
    @POST("people/reg-add")
    public Call<String> regUserAdd(
            @Body String gsonStr
    );

    /**
     * Login request. Sends basic fields to server and returns UserModel as JSON string.
     * @param email user email
     * @param pass user pass
     * @return JSON str or exception message
     */
    @GET("people/login")
    public Call<String> loginUser(
        @Query(value = "email") String email,
        @Query(value = "pass") String pass
    );

    /**
     * Request needed to add one card to user favorites.
     * @param uid user id
     * @param cid card id to be added
     * @return array list as JSON str
     */
    @GET("people/add-to-favs")
    public Call<String> addCardIdToUserFavs(
            @Query(value = "uid") long uid,
            @Query(value = "cid") long cid
    );

    /**
     * Request sends user entity and changes SocialContacts and Phone fields in case they are changed
     * @param data JSON str made from user
     * @param pass user pass
     * @return new user data
     */
    @Multipart
    @POST("people/edit-contacts")
    public Call<String> editUserContacts(
            @Part (value = "data") String data,
            @Part (value = "pass") String pass
    );

}
