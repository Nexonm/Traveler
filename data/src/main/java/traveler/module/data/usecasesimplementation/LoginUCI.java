package traveler.module.data.usecasesimplementation;

import com.google.gson.Gson;

import java.io.IOException;

import retrofit2.Call;
import traveler.module.data.repositoryImpl.UserRepositoryImpl;
import traveler.module.data.travelerapi.entityserv.UserServ;
import traveler.module.data.travelerapi.errors.UserNetAnswers;
import traveler.module.data.travelerapi.mapper.UserEntityMapper;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.travelerapi.service.APIServiceUser;
import traveler.module.domain.entity.UserEntity;

public class LoginUCI {

    public String login(String pass, String email) {
        APIServiceUser service = APIServiceTravelerConstructor.CreateService(APIServiceUser.class);
        Call<String> call = service.loginUser(email, pass);

        try {
            String ans = call.execute().body().toString();

            if (UserNetAnswers.userDoesNotExistException.equals(ans) ||
                    UserNetAnswers.userIncorrectPasswordException.equals(ans)) {
                UserRepositoryImpl.setUserMain(null);
                return ans;
            } else {
                try {
                    UserEntity user = UserEntityMapper.toUserEntityFormUserServ(
                            (new Gson()).fromJson(ans, UserServ.class),
                            true
                    );
                    UserRepositoryImpl.setUserMain(user);
                    return UserNetAnswers.userSuccessLogin;
                } catch (Exception e) {
                    return UserNetAnswers.userOtherError;
                }
            }
        }catch (IOException e){
            return UserNetAnswers.userOtherError;
        }
    }
}
