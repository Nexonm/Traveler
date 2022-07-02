package we.itschool.project.traveler.presentation.fragment.my_card_big;

import androidx.lifecycle.ViewModel;

import traveler.module.domain.entity.UserEntity;
import traveler.module.domain.usecases.user.UserDeleteCardUseCase;
import traveler.module.domain.usecases.user.UserGetMainUseCase;
import we.itschool.project.traveler.app.AppStart;

public class MyCardBigViewModel extends ViewModel {

    private final UserDeleteCardUseCase deleteCardUC = AppStart.uDeleteCardUC;
    private final UserGetMainUseCase getMainUserUC = AppStart.uGetMainUserUC;

    protected void deleteCard(long id, String user_email, String pass){
        deleteCardUC.deleteCard(id, user_email, pass);
    }

    protected UserEntity getMainUser(){
        return getMainUserUC.getMainUser();
    }
}
