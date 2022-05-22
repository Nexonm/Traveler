package we.itschool.project.traveler.domain.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.data.datamodel.CardModelPOJO;
import we.itschool.project.traveler.domain.entity.CardEntity;

public interface CardDomainRepository {

    void cardAddNew();

    void cardAddUserCardToMutableList(CardEntity card);

    void cardAddNewFavsFromServ(long cid);

    void cardCreateNew(CardModelPOJO model);

    void cardDeleteById(CardEntity card);

    void cardEditById(CardEntity card);

    MutableLiveData<ArrayList<CardEntity>> cardGetAll();

    MutableLiveData<ArrayList<CardEntity>> getUserCards();

    MutableLiveData<ArrayList<CardEntity>> getUserFavCards();

    CardEntity cardGetById(int id);

}
