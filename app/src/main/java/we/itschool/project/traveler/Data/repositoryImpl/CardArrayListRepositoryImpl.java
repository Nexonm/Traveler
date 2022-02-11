package we.itschool.project.traveler.Data.repositoryImpl;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardArrayListRepositoryImpl implements CardDomainRepository {

    @Override
    public void cardAddNew(Card card) {

    }

    @Override
    public void cardDeleteById(Card card) {

    }

    @Override
    public void cardEditById(Card card) {

    }

    @Override
    public MutableLiveData<ArrayList<Card>> cardGetAll() {
        return null;
    }

    @Override
    public Card cardGetById(int id) {
        return null;
    }
}
