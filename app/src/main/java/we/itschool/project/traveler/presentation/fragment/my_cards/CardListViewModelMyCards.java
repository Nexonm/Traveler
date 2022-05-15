package we.itschool.project.traveler.presentation.fragment.my_cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.domain.entity.CardEntity;

public class CardListViewModelMyCards extends ViewModel {

    private MutableLiveData<ArrayList<CardEntity>> cardsLiveDataList;

    public MutableLiveData<ArrayList<CardEntity>> getCardList(){
        cardsLiveDataList = new MutableLiveData<ArrayList<CardEntity>>(
                new ArrayList<>(
                        AppStart.getUser().getUserInfo().getUserCards()
                )
        );
        return cardsLiveDataList;
    }
}
