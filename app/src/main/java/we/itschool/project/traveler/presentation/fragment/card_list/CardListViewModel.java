package we.itschool.project.traveler.presentation.fragment.card_list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.usecases.CardAddNewUseCase;
import we.itschool.project.traveler.domain.usecases.CardGetAllUseCase;

public class CardListViewModel extends ViewModel {

    private final CardGetAllUseCase getAll = AppStart.cardGetAllUC;

    protected MutableLiveData<ArrayList<CardEntity>> getCardList(){
        return getAll.cardGetAll();
    }

    private final CardAddNewUseCase addNewCard = AppStart.cardAddNewUC;

    protected void addNewCard(CardEntity card){
        addNewCard.cardAddNew(card);
    }

}
