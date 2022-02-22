package we.itschool.project.traveler.data.repositoryImpl;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.data.NewData;
import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.repository.CardDomainRepository;

public class CardArrayListRepositoryImpl implements CardDomainRepository {

    private final MutableLiveData<ArrayList<Card>> dataLiveData;
    private final ArrayList<Card> data;

    {
        data = new ArrayList<>();
        dataLiveData = new MutableLiveData<>();
    }

    private static int autoIncrementId = 0;

    private static final int NUM_OF_CARDS_TO_DO;

    static {
        NUM_OF_CARDS_TO_DO = 10;
    }

    {
        for (int i = 0; i < NUM_OF_CARDS_TO_DO; i++) {
            cardAddNew(
                    NewData.newCard()
            );

        }
    }

    private void updateLiveData(){
        dataLiveData.postValue(new ArrayList<>(data));
        if (AppStart.isLog) {
            Log.w("PeopleArrayListRepositoryImpl", data.size() + "\n");
        }
    }

    @Override
    public void cardAddNew(Card card) {
        if (card.get_id() == Card.UNDEFINED_ID)
            card.set_id(autoIncrementId++);
        data.add(card);
        updateLiveData();
    }

    @Override
    public void cardDeleteById(Card card) {
        data.remove(card);
        updateLiveData();
    }

    @Override
    public void cardEditById(Card card) {
        Card card_old = cardGetById(card.get_id());
        cardDeleteById(card_old);
        cardAddNew(card);
    }

    @Override
    public MutableLiveData<ArrayList<Card>> cardGetAll() {
        return dataLiveData;
    }

    @Override
    public Card cardGetById(int id) {
        Card card = data.get(id);
        if (card != null)
            return card;
        else throw new RuntimeException("There is no element with id = " + id);
    }
}
