package we.itschool.project.traveler.presentation.fragment.card_list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.usecases.card.CardAddNewUseCase;
import we.itschool.project.traveler.domain.usecases.card.CardGetAllUseCase;

public class CardListViewModel extends ViewModel {

    private final CardAddNewUseCase addNewCard = AppStart.cardAddNewUC;
    private final CardGetAllUseCase getAll = AppStart.cardGetAllUC;

    private MutableLiveData<ArrayList<CardEntity>> cardsLiveDataList;

    protected MutableLiveData<ArrayList<CardEntity>> getCardList(){
        cardsLiveDataList = getAll.cardGetAll();
        return cardsLiveDataList;
    }

    protected void addNewCard(){
        addNewCard.cardAddNew();
    }

}
