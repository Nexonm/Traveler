package traveler.module.data.repositoryImpl.answer;

import traveler.module.domain.entity.UserEntity;

public class AnswerUserAndString {

    private UserEntity user;
    private String answer;

    public AnswerUserAndString(UserEntity user, String answer) {
        this.user = user;
        this.answer = answer;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getAnswer() {
        return answer;
    }
}
