package we.itschool.project.traveler.app;

import static traveler.module.mapapi.opentripmapapi.APIConfigOTM.API_YANDEX_MAP_KEY;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

import traveler.module.data.repositoryImpl.CardRepositoryImpl;
import traveler.module.data.repositoryImpl.UserRepositoryImpl;
import traveler.module.data.repositoryImpl.UserRepositoryImplFactory;
import traveler.module.domain.usecases.card.CardGetAllUseCase;
import traveler.module.domain.usecases.card.CardGetByIdUseCase;
import traveler.module.domain.usecases.card.CardGetBySearchUseCase;
import traveler.module.domain.usecases.card.CardUploadUseCase;
import traveler.module.domain.usecases.user.UserAddCardToFavoritesUseCase;
import traveler.module.domain.usecases.user.UserAddPhotoUseCase;
import traveler.module.domain.usecases.user.UserCreateNewCardUseCase;
import traveler.module.domain.usecases.user.UserDeleteCardUseCase;
import traveler.module.domain.usecases.user.UserEditContactsUseCase;
import traveler.module.domain.usecases.user.UserGetMainUseCase;
import traveler.module.domain.usecases.user.UserGetUserCardsUseCase;
import traveler.module.domain.usecases.user.UserGetUserFavoritesCardsUseCase;
import traveler.module.domain.usecases.user.UserLoginUserCase;
import traveler.module.domain.usecases.user.UserRegNewUseCase;

public class AppStart extends Application {

    public static UserGetMainUseCase uGetMainUserUC;
    public static UserLoginUserCase uLoginUC;
    public static UserGetUserCardsUseCase uGetUserCardsUC;
    public static UserGetUserFavoritesCardsUseCase uGetUserFavesUC;
    public static UserCreateNewCardUseCase uCreateNewCardUC;
    public static UserDeleteCardUseCase uDeleteCardUC;
    public static UserAddCardToFavoritesUseCase uAddCardToFavesUC;
    public static UserAddPhotoUseCase uAddPhotoUC;
    public static UserRegNewUseCase uRegUC;
    public static UserEditContactsUseCase uEditContactsUC;

    public static CardGetAllUseCase cGetAllUC;
    public static CardGetBySearchUseCase cGetBySearchUC;
    public static CardGetByIdUseCase cGetByIdUC;
    public static CardUploadUseCase cUploadUC;

    private static AppStart instance;


    private int displayHeight;
    private int displayWidth;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        UserRepositoryImpl userRepImpl = UserRepositoryImplFactory.createUserRepositoryImpl();

        uGetMainUserUC = new UserGetMainUseCase(userRepImpl);
        uLoginUC = new UserLoginUserCase(userRepImpl);
        uGetUserCardsUC = new UserGetUserCardsUseCase(userRepImpl);
        uGetUserFavesUC = new UserGetUserFavoritesCardsUseCase(userRepImpl);
        uCreateNewCardUC = new UserCreateNewCardUseCase(userRepImpl);
        uDeleteCardUC = new UserDeleteCardUseCase(userRepImpl);
        uAddCardToFavesUC = new UserAddCardToFavoritesUseCase(userRepImpl);
        uAddPhotoUC = new UserAddPhotoUseCase(userRepImpl);
        uRegUC = new UserRegNewUseCase(userRepImpl);
        uEditContactsUC = new UserEditContactsUseCase(userRepImpl);

        CardRepositoryImpl cardRepImpl = new CardRepositoryImpl();

        cUploadUC = new CardUploadUseCase(cardRepImpl);
        cGetAllUC = new CardGetAllUseCase(cardRepImpl);
        cGetBySearchUC = new CardGetBySearchUseCase(cardRepImpl);
        cGetByIdUC = new CardGetByIdUseCase(cardRepImpl);
        cUploadUC = new CardUploadUseCase(cardRepImpl);

        //set and init mapkit_api before creating view with map
        MapKitFactory.setApiKey(API_YANDEX_MAP_KEY);
    }


    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public static AppStart getInstance(){
        return instance;
    }
}
