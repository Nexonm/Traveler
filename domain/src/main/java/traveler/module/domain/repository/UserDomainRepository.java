package traveler.module.domain.repository;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.entity.UserEntity;

public interface UserDomainRepository {

    /**
     * Get main user object for person uses this app.
     * Object contains all fields connected to this person.
     * @return user object with some data
     */
    UserEntity getMainUserForThisApp();

    /**
     * Login new user and get some data needed for work.
     * @param pass user password
     * @param login user login
     */
    void login(String pass, String login);

    /**
     * Gets all cards, that user had created. Uses id to find user.
     * @return list of user cards
     */
    ArrayList<CardEntity> getUserCards();

    /**
     * Gets all cards that user marked as favorites. Uses id to find user.
     * @param id id of user
     * @return list of user favorite cards
     */
    ArrayList<CardEntity> getUserFavoritesCards(long id);

    /**
     * Add some card to user favorites card list. Uses card id to find card.
     * @param id id of card
     */
    void addCardToFavorites(long id);

    /**
     * User can create new Card.
     * @param createdCard card that user created
     */
    void createNewCard(CardEntity createdCard);

    /**
     * Add new photo to user.
     * (That's not good passing path as string cause
     * photo is photo but not String.
     * Doing this breaks CleanArchitecture principles.
     * Business rules can't know about the machine.)
     * @param path way to photo in a machine
     */
    void addPhoto(String path);

    /**
     * In case there is new user and we need to reg him.
     * @param newUser data represented as UserEntity
     */
    void regNew(UserEntity newUser, String password);

    /**
     * Sets interests to user.
     * @param interests the user interests as string
     */
    void setInterests(String interests);

    /**
     * Sets description to user.
     * @param desc hte user personal description
     */
    void setDescription(String desc);
}
