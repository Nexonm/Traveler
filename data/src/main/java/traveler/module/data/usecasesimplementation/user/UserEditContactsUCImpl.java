package traveler.module.data.usecasesimplementation.user;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import traveler.module.data.travelerapi.entityserv.UserServ;
import traveler.module.data.travelerapi.errors.UserNetAnswers;
import traveler.module.data.travelerapi.mapper.UserEntityMapper;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.travelerapi.service.APIServiceUser;
import traveler.module.data.usecaseinterface.user.UserEditContactsUCI;
import traveler.module.domain.entity.UserEntity;

public class UserEditContactsUCImpl implements UserEditContactsUCI {

    @Override
    public UserEntity editContacts(UserEntity entity, String pass) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);
        //make str to send just needed data, not all
        JSONObject json = new JSONObject();
        try {
            json.put("email", entity.getUserInfo().getEmail());
            json.put("phoneNumber", entity.getUserInfo().getPhoneNumber());
            json.put("socialContacts", entity.getUserInfo().getSocialContacts());
        } catch (JSONException e) {
//            Log.v("retrofitLogger", "some mistake JSONObject" + e.getMessage());
            e.printStackTrace();
        }

        Log.v("EDIT_USER_CONTACTS", "data @param as JSON str : " + json.toString());

        //make call
        Call<String> call = service.editUserContacts(json.toString(), pass);
        try {
            String ans = "waiting";
            Response<String> response = call.execute();

            if (response.isSuccessful()) {
                ans = response.body().toString();
                Log.v("EDIT_USER_CONTACTS", "response is SUCCESSFUL, ans = " + ans);
            } else {
                ans = response.errorBody().string().toString();
                Log.v("EDIT_USER_CONTACTS", "response is unsuccessful, ans = " + ans);
            }

            if (UserNetAnswers.userDoesNotExistException.equals(ans) ||
                    UserNetAnswers.userIncorrectPasswordException.equals(ans) ||
                    UserNetAnswers.userDataFormatException.equals(ans) ||
                    "waiting".equals(ans)) {
                return null;
            } else {
                try {
                    return UserEntityMapper.toUserEntityFormUserServ(
                            (new Gson()).fromJson(ans, UserServ.class),
                            true
                    );
                } catch (Exception e) {
                    return null;
                }
            }

        } catch (IOException e) {
            return null;
        }
    }
}
