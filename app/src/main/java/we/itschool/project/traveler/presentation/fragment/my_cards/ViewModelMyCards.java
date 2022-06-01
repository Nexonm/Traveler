package we.itschool.project.traveler.presentation.fragment.my_cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.usecases.user.UserCreateNewCardUseCase;
import traveler.module.domain.usecases.user.UserGetUserCardsUseCase;
import we.itschool.project.traveler.app.AppStart;

public class ViewModelMyCards extends ViewModel {

    private MutableLiveData<ArrayList<CardEntity>> cardsLiveDataList;
    UserGetUserCardsUseCase getCardsUC = AppStart.uGetUserCardsUC;
    UserCreateNewCardUseCase addOneUC = AppStart.uCreateNewCardUC;

    public ViewModelMyCards() {
        cardsLiveDataList = new MutableLiveData<>();
    }

    public MutableLiveData<ArrayList<CardEntity>> getCardList(){
        cardsLiveDataList.postValue(getCardsUC.getUserCards());
        return cardsLiveDataList;
    }

//    protected void addOne(CardEntity card){
//        addOneUC.cardAddUserCardToMutableList(card);
//    }
}
