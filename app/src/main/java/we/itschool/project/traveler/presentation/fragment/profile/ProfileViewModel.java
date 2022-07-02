package we.itschool.project.traveler.presentation.fragment.profile;

import androidx.lifecycle.ViewModel;

import traveler.module.domain.entity.UserEntity;
import traveler.module.domain.usecases.user.UserEditContactsUseCase;
import traveler.module.domain.usecases.user.UserGetMainUseCase;
import we.itschool.project.traveler.app.AppStart;

public class ProfileViewModel extends ViewModel {

    private final UserGetMainUseCase getMainUserUC = AppStart.uGetMainUserUC;
    private final UserEditContactsUseCase editContactsUC = AppStart.uEditContactsUC;
    private final UserEntity user;

    public ProfileViewModel() {
        user = updateUserMain();
    }

    protected UserEntity updateUserMain() {
        return getMainUserUC.getMainUser();
    }

    protected UserEntity getMainUser() {
        return user;
    }

    protected void editContacts(UserEntity entity, String pass) {
        editContactsUC.editContacts(entity, pass);
    }
}
