package we.itschool.project.traveler.app;

import android.app.Application;

import we.itschool.project.traveler.Data.repositoryImpl.CardArrayListRepositoryImpl;
import we.itschool.project.traveler.domain.usecases.CardAddNewUseCase;
import we.itschool.project.traveler.domain.usecases.CardDeleteByIdUseCase;
import we.itschool.project.traveler.domain.usecases.CardEditByIdUseCase;
import we.itschool.project.traveler.domain.usecases.CardGetAllUseCase;
import we.itschool.project.traveler.domain.usecases.CardGetByIdUseCase;

public class AppStart extends Application {
    public static final boolean isLog = true;

    public static CardArrayListRepositoryImpl imp;

    public static CardGetAllUseCase cardGetAllUC;
    public static CardGetByIdUseCase cardGetByIdUC;
    public static CardAddNewUseCase cardAddNewUC;
    public static CardEditByIdUseCase cardEditByIdUC;
    public static CardDeleteByIdUseCase cardDeleteByIdUC;


    @Override
    public void onCreate() {
        super.onCreate();

        imp = new CardArrayListRepositoryImpl();
        cardGetAllUC = new CardGetAllUseCase(imp);
        cardGetByIdUC = new CardGetByIdUseCase(imp);
        cardAddNewUC = new CardAddNewUseCase(imp);
        cardEditByIdUC = new CardEditByIdUseCase(imp);
        cardDeleteByIdUC = new CardDeleteByIdUseCase(imp);

    }
}
