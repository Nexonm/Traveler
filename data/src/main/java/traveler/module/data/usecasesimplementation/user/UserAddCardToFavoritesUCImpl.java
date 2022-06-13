package traveler.module.data.usecasesimplementation.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.travelerapi.service.APIServiceUser;
import traveler.module.data.usecaseinterface.user.UserAddCardToFavoritesUCI;

public class UserAddCardToFavoritesUCImpl implements UserAddCardToFavoritesUCI {

    @Override
    public ArrayList<Long> addCardToFavorites(long uid, long cid) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);

        Call<String> call = service.addCardIdToUserFavs(uid, cid);

        try {
            String ans = call.execute().body().toString();
            return (new Gson()).fromJson(
                    ans,
                    new TypeToken<ArrayList<Long>>() {
                    }.getType()
            );
        }catch (IOException e){
            return null;
        }
    }
}
