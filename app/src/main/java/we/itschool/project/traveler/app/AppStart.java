package we.itschool.project.traveler.app;

import android.app.Application;

import we.itschool.project.traveler.data.repositoryImpl.CardArrayListRepositoryImpl;
import we.itschool.project.traveler.domain.usecases.CardAddNewUseCase;
import we.itschool.project.traveler.domain.usecases.CardDeleteByIdUseCase;
import we.itschool.project.traveler.domain.usecases.CardEditByIdUseCase;
import we.itschool.project.traveler.domain.usecases.CardGetAllUseCase;
import we.itschool.project.traveler.domain.usecases.CardGetByIdUseCase;
import we.itschool.project.traveler.data.repositoryImpl.PersonArrayListRepositoryImpl;
import we.itschool.project.traveler.domain.usecases.PersonAddNewUseCase;
import we.itschool.project.traveler.domain.usecases.PersonDeleteByIdUseCase;
import we.itschool.project.traveler.domain.usecases.PersonEditByIdUseCase;
import we.itschool.project.traveler.domain.usecases.PersonGetAllUseCase;
import we.itschool.project.traveler.domain.usecases.PersonGetByIdUseCase;

public class AppStart extends Application {
    public static final boolean isLog = true;

    public static CardArrayListRepositoryImpl imp;

    public static CardGetAllUseCase cardGetAllUC;
    public static CardGetByIdUseCase cardGetByIdUC;
    public static CardAddNewUseCase cardAddNewUC;
    public static CardEditByIdUseCase cardEditByIdUC;
    public static CardDeleteByIdUseCase cardDeleteByIdUC;

    public static PersonArrayListRepositoryImpl imp1;

    public static PersonGetAllUseCase personGetAllUC;
    public static PersonGetByIdUseCase personGetByIdUC;
    public static PersonAddNewUseCase personAddNewUC;
    public static PersonEditByIdUseCase personEditByIdUC;
    public static PersonDeleteByIdUseCase personDeleteByIdUC;

    @Override
    public void onCreate() {
        super.onCreate();

        imp = new CardArrayListRepositoryImpl();
        cardGetAllUC = new CardGetAllUseCase(imp);
        cardGetByIdUC = new CardGetByIdUseCase(imp);
        cardAddNewUC = new CardAddNewUseCase(imp);
        cardEditByIdUC = new CardEditByIdUseCase(imp);
        cardDeleteByIdUC = new CardDeleteByIdUseCase(imp);

        imp1 = new PersonArrayListRepositoryImpl();
        personGetAllUC = new PersonGetAllUseCase(imp1);
        personGetByIdUC = new PersonGetByIdUseCase(imp1);
        personAddNewUC = new PersonAddNewUseCase(imp1);
        personEditByIdUC = new PersonEditByIdUseCase(imp1);
        personDeleteByIdUC = new PersonDeleteByIdUseCase(imp1);
    }
}
