package traveler.module.data.usecaseinterface.user;

import traveler.module.domain.entity.CardEntity;

public interface UserCreateNewCardUCI {

    void createNewCard(CardEntity createdCard, long userId);
}
