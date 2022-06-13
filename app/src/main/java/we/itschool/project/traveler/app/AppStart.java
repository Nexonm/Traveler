package we.itschool.project.traveler.app;

import static traveler.module.mapapi.opentripmapapi.APIConfigOTM.API_YANDEX_MAP_KEY;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

import traveler.module.data.repositoryImpl.CardRepositoryImpl;
import traveler.module.data.repositoryImpl.UserRepositoryImpl;
import traveler.module.data.repositoryImpl.UserRepositoryImplFactory;
import traveler.module.domain.usecases.card.CardDeleteUseCase;
import traveler.module.domain.usecases.card.CardGetAllUseCase;
import traveler.module.domain.usecases.card.CardGetByIdUseCase;
import traveler.module.domain.usecases.card.CardGetBySearchUseCase;
import traveler.module.domain.usecases.card.CardUploadUseCase;
import traveler.module.domain.usecases.user.UserAddCardToFavoritesUseCase;
import traveler.module.domain.usecases.user.UserAddPhotoUseCase;
import traveler.module.domain.usecases.user.UserCreateNewCardUseCase;
import traveler.module.domain.usecases.user.UserGetMainUseCase;
import traveler.module.domain.usecases.user.UserGetUserCardsUseCase;
import traveler.module.domain.usecases.user.UserGetUserFavoritesCardsUseCase;
import traveler.module.domain.usecases.user.UserLoginUserCase;
import traveler.module.domain.usecases.user.UserRegNewUseCase;

public class AppStart extends Application {
    public static final boolean isLog = true;

    private static UserRepositoryImpl userRepImpl;

    public static UserGetMainUseCase uGetMainUserUC;
    public static UserLoginUserCase uLoginUC;
    public static UserGetUserCardsUseCase uGetUserCardsUC;
    public static UserGetUserFavoritesCardsUseCase uGetUserFavsUC;
    public static UserCreateNewCardUseCase uCreateNewCardUC;
    public static UserAddCardToFavoritesUseCase uAddCardToFavsUC;
    public static UserAddPhotoUseCase uAddPhotoUC;
    public static UserRegNewUseCase uRegUC;

    private static CardRepositoryImpl cardRepImpl;

    public static CardGetAllUseCase cGetAllUC;
    public static CardGetBySearchUseCase cGetBySearchUC;
    public static CardGetByIdUseCase cGetByIdUC;
    public static CardDeleteUseCase cDeleteUC;
    public static CardUploadUseCase cUploadUC;

    private static AppStart instance;

//    private static UserEntity user;

    private int displayHeight;
    private int displayWidth;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        userRepImpl = UserRepositoryImplFactory.createUserRepositoryImpl();

        uGetMainUserUC = new UserGetMainUseCase(userRepImpl);
        uLoginUC = new UserLoginUserCase(userRepImpl);
        uGetUserCardsUC = new UserGetUserCardsUseCase(userRepImpl);
        uGetUserFavsUC = new UserGetUserFavoritesCardsUseCase(userRepImpl);
        uCreateNewCardUC = new UserCreateNewCardUseCase(userRepImpl);
        uAddCardToFavsUC = new UserAddCardToFavoritesUseCase(userRepImpl);
        uAddPhotoUC = new UserAddPhotoUseCase(userRepImpl);
        uRegUC = new UserRegNewUseCase(userRepImpl);

        cardRepImpl = new CardRepositoryImpl();

        cUploadUC = new CardUploadUseCase(cardRepImpl);
        cGetAllUC = new CardGetAllUseCase(cardRepImpl);
        cGetBySearchUC = new CardGetBySearchUseCase(cardRepImpl);
        cGetByIdUC = new CardGetByIdUseCase(cardRepImpl);
        cDeleteUC = new CardDeleteUseCase(cardRepImpl);
        cUploadUC = new CardUploadUseCase(cardRepImpl);

//        user = null;

        //set and init mapkit_api before creating view with map
        MapKitFactory.setApiKey(API_YANDEX_MAP_KEY);
    }


    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

//    public static UserEntity getUser() {
//        return AppStart.user;
//    }
//
//    public static void setUser(UserEntity user) {
//        if (AppStart.user==null)
//        AppStart.user = user;
//        if (AppStart.user.getUserInfo().getUserCards()==null)
//            AppStart.user.getUserInfo().setUserCards(new ArrayList<>());
//    }

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
