package traveler.module.data.repositoryImpl;

import traveler.module.data.usecasesimplementation.user.UserAddCardToFavoritesUCImpl;
import traveler.module.data.usecasesimplementation.user.UserAddPhotoUCImpl;
import traveler.module.data.usecasesimplementation.user.UserCreateNewCardUCImpl;
import traveler.module.data.usecasesimplementation.user.UserLoginUCImpl;
import traveler.module.data.usecasesimplementation.user.UserRegUCImpl;

public class UserRepositoryImplFactory {

    public static UserRepositoryImpl createUserRepositoryImpl(){
        return new UserRepositoryImpl(
                new UserLoginUCImpl(),
                new UserRegUCImpl(),
                new UserAddPhotoUCImpl(),
                new UserCreateNewCardUCImpl(),
                new UserAddCardToFavoritesUCImpl()
        );
    }
}
