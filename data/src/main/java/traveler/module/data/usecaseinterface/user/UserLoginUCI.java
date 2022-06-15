package traveler.module.data.usecaseinterface.user;

import traveler.module.data.repositoryImpl.answer.AnswerUserAndString;

public interface UserLoginUCI {

    AnswerUserAndString login(String login, String pass);
}
