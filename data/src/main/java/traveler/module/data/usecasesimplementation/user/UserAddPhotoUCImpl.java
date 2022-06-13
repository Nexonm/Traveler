package traveler.module.data.usecasesimplementation.user;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import traveler.module.data.travelerapi.errors.UserNetAnswers;
import traveler.module.data.travelerapi.service.APIServiceStorage;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.usecaseinterface.user.UserAddPhotoUCI;

public class UserAddPhotoUCImpl implements UserAddPhotoUCI {

    @Override
    public String addPhoto(String path, long userId) {

        APIServiceStorage service = APIServiceTravelerConstructor.CreateService(APIServiceStorage.class);
        //pass it like this
//        Log.v("retrofitLogger", "get file, pathToPhoto is: " + path);
        File file = new File(path);
//            Log.v("retrofitLogger", "fill it in request Body");
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

//          MultipartBody.Part is used to send also the actual file name
//            Log.v("retrofitLogger", "fill it in multipart Body");
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//            Log.v("retrofitLogger", "Make call");
        RequestBody cidBody = RequestBody.create(MediaType.parse("multipart/from-data"), String.valueOf(userId));
        Call<String> call = service.uploadPhotoToUser(body, cidBody);

        try {
            String ans = call.execute().body().toString();
            if (UserNetAnswers.userPhotoLoadingException.equals(ans)){
                return UserNetAnswers.userOtherError;
            }else{
                return ans;
            }
        }catch (IOException e){
            return UserNetAnswers.userOtherError;
        }
    }
}
