package traveler.module.data.usecasesimplementation.user;

import android.util.Log;

import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import traveler.module.data.travelerapi.entityserv.CardServ;
import traveler.module.data.travelerapi.mapper.CardEntityMapper;
import traveler.module.data.travelerapi.service.APIServiceCard;
import traveler.module.data.travelerapi.service.APIServiceStorage;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.usecaseinterface.user.UserCreateNewCardUCI;
import traveler.module.domain.entity.CardEntity;

public class UserCreateNewCardUCImpl implements UserCreateNewCardUCI {

    @Override
    public void createNewCard(CardEntity createdCard, long userID) {
        APIServiceCard service = APIServiceTravelerConstructor.CreateService(APIServiceCard.class);
        Call<String> call = service.addNewCardGson(userID, CardEntityMapper.toCardServFromCardEntity(createdCard));
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
}
