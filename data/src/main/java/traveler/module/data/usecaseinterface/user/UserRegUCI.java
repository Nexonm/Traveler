package traveler.module.data.usecaseinterface.user;

import traveler.module.data.repositoryImpl.answer.AnswerUserAndString;
import traveler.module.domain.entity.UserEntity;

public interface UserRegUCI {

    AnswerUserAndString regNew(UserEntity user, String pass);
}
