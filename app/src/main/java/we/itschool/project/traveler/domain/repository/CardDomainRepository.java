package we.itschool.project.traveler.domain.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.Card;

public interface CardDomainRepository {

    void cardAddNew(Card card);

    void cardDeleteById(Card card);

    void cardEditById(Card card);

    MutableLiveData<ArrayList<Card>> cardGetAll();

    Card cardGetById(int id);

}
