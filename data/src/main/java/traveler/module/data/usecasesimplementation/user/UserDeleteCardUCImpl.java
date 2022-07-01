package traveler.module.data.usecasesimplementation.user;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import traveler.module.data.travelerapi.service.APIServiceCard;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.usecaseinterface.user.UserDeleteCardUCI;

public class UserDeleteCardUCImpl implements UserDeleteCardUCI {

    @Override
    public void deleteCard(long id, String uemail, String pass) {
        APIServiceCard service = APIServiceTravelerConstructor.CreateService(APIServiceCard.class);
        Call<String> call = service.deleteCardById(id, uemail, pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body() != null) {

                } else {
//                    try {
//                        Log.v("OkHttpClient DELETE_CARD", "response.errorBody().toString() = " + response.errorBody().string().toString());
//                    }catch (IOException e){
//                        Log.v("OkHttpClient DELETE_CARD", "eeeeerrrroooorrrr = " +e.getMessage());
//                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
//                Log.v("OkHttpClient DELETE_CARD", "call.request().body().toString() = " + call.request().body());
//                Log.v("OkHttpClient DELETE_CARD", "t.getMessage().toString() = " + t.getMessage().toString());
            }
        });

    }
}
