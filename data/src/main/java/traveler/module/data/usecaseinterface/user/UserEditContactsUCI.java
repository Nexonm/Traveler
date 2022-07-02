package traveler.module.data.usecaseinterface.user;

import traveler.module.domain.entity.UserEntity;

public interface UserEditContactsUCI {

    UserEntity editContacts(UserEntity entity, String pass);
}
