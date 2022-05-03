package we.itschool.project.traveler.app;

import android.app.Application;

import we.itschool.project.traveler.data.repositoryImpl.CardArrayListRepositoryImpl;
import we.itschool.project.traveler.domain.usecases.CardAddNewUseCase;
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
import we.itschool.project.traveler.domain.usecases.UserGetByIdUseCase;

public class AppStart extends Application {
    public static final boolean isLog = true;

    public static CardArrayListRepositoryImpl imp;

    public static CardGetAllUseCase cardGetAllUC;
    public static CardGetByIdUseCase cardGetByIdUC;
    public static CardAddNewUseCase cardAddNewUC;
    public static CardCreateNewUseCase cardCreateNewUC;
    public static CardEditByIdUseCase cardEditByIdUC;
    public static CardDeleteByIdUseCase cardDeleteByIdUC;

    public static UserArrayListRepositoryImpl imp1;

    public static UserGetAllUseCase personGetAllUC;
    public static UserGetByIdUseCase personGetByIdUC;
    public static UserAddNewUseCase personAddNewUC;
    public static UserEditByIdUseCase personEditByIdUC;
    public static UserDeleteByIdUseCase personDeleteByIdUC;

    private static AppStart instance;

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

        imp1 = new UserArrayListRepositoryImpl();
        personGetAllUC = new UserGetAllUseCase(imp1);
        personGetByIdUC = new UserGetByIdUseCase(imp1);
        personAddNewUC = new UserAddNewUseCase(imp1);
        personEditByIdUC = new UserEditByIdUseCase(imp1);
        personDeleteByIdUC = new UserDeleteByIdUseCase(imp1);
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
