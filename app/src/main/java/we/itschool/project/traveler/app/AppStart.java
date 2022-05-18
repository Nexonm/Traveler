package we.itschool.project.traveler.app;

import android.app.Application;

import java.util.ArrayList;

import we.itschool.project.traveler.data.repositoryImpl.CardArrayListRepositoryImpl;
import we.itschool.project.traveler.domain.entity.UserEntity;
import we.itschool.project.traveler.domain.usecases.CardAddNewUseCase;
import we.itschool.project.traveler.domain.usecases.CardAddOneToMutableListUseCase;
import we.itschool.project.traveler.domain.usecases.CardCreateNewUseCase;
import we.itschool.project.traveler.domain.usecases.CardDeleteByIdUseCase;
import we.itschool.project.traveler.domain.usecases.CardEditByIdUseCase;
import we.itschool.project.traveler.domain.usecases.CardGetAllUseCase;
import we.itschool.project.traveler.domain.usecases.CardGetByIdUseCase;
import we.itschool.project.traveler.data.repositoryImpl.UserArrayListRepositoryImpl;
import we.itschool.project.traveler.domain.usecases.UserAddNewUseCase;
import we.itschool.project.traveler.domain.usecases.UserDeleteByIdUseCase;
import we.itschool.project.traveler.domain.usecases.UserEditByIdUseCase;
import we.itschool.project.traveler.domain.usecases.UserGetAllUseCase;
import we.itschool.project.traveler.domain.usecases.CardGetAllUserCardsUseCase;
import we.itschool.project.traveler.domain.usecases.UserGetByIdUseCase;
import we.itschool.project.traveler.domain.usecases.UserLoginUseCase;

public class AppStart extends Application {
    public static final boolean isLog = true;

    public static CardArrayListRepositoryImpl imp;

    public static CardGetAllUseCase cardGetAllUC;
    public static CardGetByIdUseCase cardGetByIdUC;
    public static CardAddNewUseCase cardAddNewUC;
    public static CardCreateNewUseCase cardCreateNewUC;
    public static CardEditByIdUseCase cardEditByIdUC;
    public static CardDeleteByIdUseCase cardDeleteByIdUC;
    public static CardGetAllUserCardsUseCase userGetCardsUC;
    public static CardAddOneToMutableListUseCase addToMListUC;

    public static UserArrayListRepositoryImpl imp1;

    public static UserGetAllUseCase personGetAllUC;
    public static UserGetByIdUseCase personGetByIdUC;
    public static UserAddNewUseCase personAddNewUC;
    public static UserEditByIdUseCase personEditByIdUC;
    public static UserDeleteByIdUseCase personDeleteByIdUC;
    public static UserLoginUseCase loginUC;

    private static AppStart instance;

    private static UserEntity user;

    private int displayHeight;
    private int displayWidth;


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        imp = new CardArrayListRepositoryImpl();
        cardGetAllUC = new CardGetAllUseCase(imp);
        cardGetByIdUC = new CardGetByIdUseCase(imp);
        cardAddNewUC = new CardAddNewUseCase(imp);
        cardCreateNewUC = new CardCreateNewUseCase(imp);
        cardEditByIdUC = new CardEditByIdUseCase(imp);
        cardDeleteByIdUC = new CardDeleteByIdUseCase(imp);
        userGetCardsUC = new CardGetAllUserCardsUseCase(imp);
        addToMListUC = new CardAddOneToMutableListUseCase(imp);

        imp1 = new UserArrayListRepositoryImpl();
        personGetAllUC = new UserGetAllUseCase(imp1);
        personGetByIdUC = new UserGetByIdUseCase(imp1);
        personAddNewUC = new UserAddNewUseCase(imp1);
        personEditByIdUC = new UserEditByIdUseCase(imp1);
        personDeleteByIdUC = new UserDeleteByIdUseCase(imp1);
        loginUC = new UserLoginUseCase(imp1);

        user = null;
    }


    public void setDisplayHeight(int displayHeight) {
        this.displayHeight = displayHeight;
    }

    public void setDisplayWidth(int displayWidth) {
        this.displayWidth = displayWidth;
    }

    public static UserEntity getUser() {
        return AppStart.user;
    }

    public static void setUser(UserEntity user) {
        if (AppStart.user==null)
        AppStart.user = user;
        if (AppStart.user.getUserInfo().getUserCards()==null)
            AppStart.user.getUserInfo().setUserCards(new ArrayList<>());
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
