package traveler.module.data.usecasesimplementation.user;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import traveler.module.data.repositoryImpl.answer.AnswerUserAndString;
import traveler.module.data.travelerapi.entityserv.UserServ;
import traveler.module.data.travelerapi.errors.UserNetAnswers;
import traveler.module.data.travelerapi.mapper.UserEntityMapper;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.travelerapi.service.APIServiceUser;
import traveler.module.data.usecaseinterface.user.UserRegUCI;
import traveler.module.domain.entity.UserEntity;

public class UserRegUCImpl implements UserRegUCI {

    public AnswerUserAndString regNew(UserEntity newUser, String pass) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);

        JSONObject json = new JSONObject();
        try {
            json.put("email", newUser.getUserInfo().getEmail());
            json.put("password", pass);
            json.put("firstName", newUser.getUserInfo().getFirstName());
            json.put("secondName", newUser.getUserInfo().getSecondName());
            json.put("dateOfBirth", newUser.getUserInfo().getDateOfBirth());
            json.put("phoneNumber", newUser.getUserInfo().getPhoneNumber());
            json.put("isMale", newUser.getUserInfo().isMale());
            json.put("socialContacts", newUser.getUserInfo().getSocialContacts());
        } catch (JSONException e) {
//            Log.v("retrofitLogger", "some mistake JSONObject" + e.getMessage());
            e.printStackTrace();
        }


//        Log.v("retrofitLogger", "add User to Server, model.toJson():" + json.toString());
        Call<String> call = service.regUserMain(json.toString());

        try {
            String ans = "";
            Response<String> response = call.execute();
            Log.v("OkHttpClient REGISTRATION", "Response.isSuccessful() = " + response.isSuccessful());
            if (response.isSuccessful()) {
                Log.v("OkHttpClient REGISTRATION", "response.body() = " + response.body());
                ans = response.body().toString();
            } else {
                ans = response.errorBody().string();
                Log.v("OkHttpClient REGISTRATION", "(ans) response.errorBody().string = " + ans);
            }

            if (UserNetAnswers.userAlreadyExists.equals(ans)) {
                Log.v("OkHttpClient REGISTRATION", "User already exists, registration cannot be completed");
                return new AnswerUserAndString(null, UserNetAnswers.userAlreadyExists);
            } else {
                try {
                    Log.v("OkHttpClient REGISTRATION", "User is regged, user created");
                    return new AnswerUserAndString(
                            UserEntityMapper.toUserEntityFormUserServ(
                                    (new Gson()).fromJson(ans, UserServ.class),
                                    true),
                            UserNetAnswers.userSuccessRegistration);
                } catch (Exception e) {
                    Log.v("OkHttpClient REGISTRATION", "Exception while try make usr from answer: " + e.getMessage().toString());
                    return new AnswerUserAndString(null, UserNetAnswers.userOtherError);
                }
            }
        } catch (IOException e) {
            Log.v("OkHttpClient REGISTRATION", "Exception in all request");
            return new AnswerUserAndString(null, UserNetAnswers.userOtherError);
        }
    }
}
