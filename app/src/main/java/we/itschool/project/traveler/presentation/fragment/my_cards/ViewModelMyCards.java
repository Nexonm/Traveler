package we.itschool.project.traveler.presentation.fragment.my_cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.usecases.card.CardAddOneToMutableListUseCase;
import we.itschool.project.traveler.domain.usecases.card.CardGetAllUserCardsUseCase;

public class ViewModelMyCards extends ViewModel {

    private MutableLiveData<ArrayList<CardEntity>> cardsLiveDataList;
    CardGetAllUserCardsUseCase getCardsUC = AppStart.userGetCardsUC;
    CardAddOneToMutableListUseCase addOneUC = AppStart.addToMListUC;

    public MutableLiveData<ArrayList<CardEntity>> getCardList(){
        cardsLiveDataList = getCardsUC.getUserCards();
        return cardsLiveDataList;
    }

    protected void addOne(CardEntity card){
        addOneUC.cardAddUserCardToMutableList(card);
    }
}
