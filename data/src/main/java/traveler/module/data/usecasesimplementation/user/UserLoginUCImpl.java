package traveler.module.data.usecasesimplementation.user;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import traveler.module.data.repositoryImpl.answer.AnswerUserAndString;
import traveler.module.data.travelerapi.entityserv.UserServ;
import traveler.module.data.travelerapi.errors.UserNetAnswers;
import traveler.module.data.travelerapi.mapper.UserEntityMapper;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.travelerapi.service.APIServiceUser;
import traveler.module.data.usecaseinterface.user.UserLoginUCI;

public class UserLoginUCImpl implements UserLoginUCI {

    public AnswerUserAndString login(String login, String pass) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);
        Call<String> call = service.loginUser(login, pass);

        try {
            String ans = "waiting";
            Response<String> response = call.execute();
            Log.v("OkHttpClient LOGIN", "Response.isSuccessful() = " + response.isSuccessful());
            if (response.isSuccessful()) {
                Log.v("OkHttpClient LOGIN", "response.body() = " + response.body());
                ans = response.body().toString();
            } else {
//                Log.v("OkHttpClient LOGIN", "response.errorBody().string = " + response.errorBody().string());
                ans = response.errorBody().string().toString();
                Log.v("OkHttpClient LOGIN", "(ans) response.errorBody().string = " + ans);

            }
            Log.v("OkHttpClient LOGIN", "Check what is ANS: " + ans);

            if (UserNetAnswers.userDoesNotExistException.equals(ans) ||
                    UserNetAnswers.userIncorrectPasswordException.equals(ans)) {
                Log.v("OkHttpClient LOGIN", "Exception from server!!!");
                return new AnswerUserAndString(null, ans);
            } else {
                try {
                    Log.v("OkHttpClient LOGIN", "User is logged, user creation started");
                    return new AnswerUserAndString(
                            UserEntityMapper.toUserEntityFormUserServ(
                                    (new Gson()).fromJson(ans, UserServ.class),
                                    true),
                            UserNetAnswers.userSuccessLogin);
                } catch (Exception e) {
                    Log.v("OkHttpClient LOGIN", "Exception while try make usr from answer: " + e.getMessage().toString());
                    return new AnswerUserAndString(null, UserNetAnswers.userOtherError);
                }
            }
        } catch (IOException e) {
            Log.v("OkHttpClient LOGIN", "Exception in all request");
            return new AnswerUserAndString(null, UserNetAnswers.userOtherError);
        }
    }
}
