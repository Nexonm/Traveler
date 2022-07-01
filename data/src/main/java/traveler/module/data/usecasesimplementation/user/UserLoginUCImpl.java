package traveler.module.data.usecasesimplementation.user;

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

            if (response.isSuccessful()) {
                ans = response.body().toString();
            } else {
                ans = response.errorBody().string().toString();
            }

            if (UserNetAnswers.userDoesNotExistException.equals(ans) ||
                    UserNetAnswers.userIncorrectPasswordException.equals(ans)) {
                return new AnswerUserAndString(null, ans);
            } else {
                try {
                    return new AnswerUserAndString(
                            UserEntityMapper.toUserEntityFormUserServ(
                                    (new Gson()).fromJson(ans, UserServ.class),
                                    true),
                            UserNetAnswers.userSuccessLogin);
                } catch (Exception e) {
                    return new AnswerUserAndString(null, UserNetAnswers.userOtherError);
                }
            }
        } catch (IOException e) {
            return new AnswerUserAndString(null, UserNetAnswers.userOtherError);
        }
    }
}
