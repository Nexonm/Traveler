package traveler.module.domain.repository;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;

public interface CardDomainRepository {

    /**
     * Get all cards from some storage.
     * @return list of CardEntity from storage
     */
    ArrayList<CardEntity> getAll();

    /**
     * Get all cards from some storage searched with some searchStr param.
     * @param searchStr key word to find data
     * @return list of CardEntity from storage
     */
    ArrayList<CardEntity> getBySearch(String searchStr);

    /**
     * Delete card by it's special id in storage.
     * @param id of card
     */
    void delete(long id);

    /**
     * Calls method to update the storage list and add new card.
     */
    void upload();

    /**
     * Calls method to get some card by it's id.
     */
    CardEntity getById(long id);
    /**
     * Edit some data in card.
     * @param newCard card with edited data.
     */
    void edit(CardEntity newCard);

}
