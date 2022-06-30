package we.itschool.project.traveler.presentation.fragment.card_list;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.usecases.card.CardGetAllUseCase;
import traveler.module.domain.usecases.card.CardGetBySearchUseCase;
import traveler.module.domain.usecases.card.CardUploadUseCase;
import we.itschool.project.traveler.app.AppStart;

public class CardListViewModel extends ViewModel {

    private final CardUploadUseCase addNewCard = AppStart.cUploadUC;
    private final CardGetAllUseCase getAll = AppStart.cGetAllUC;
    private final CardGetBySearchUseCase searchCards = AppStart.cGetBySearchUC;

    private final MutableLiveData<ArrayList<CardEntity>> cardsLiveDataList = new MutableLiveData<>();

    protected MutableLiveData<ArrayList<CardEntity>> getCardList(){
        cardsLiveDataList.postValue(getAll.getAll());
        return cardsLiveDataList;
    }

    protected void addNewCard(){
        addNewCard.upload();
    }

    protected void searchCards(String str){
        searchCards.getBySearch(str);
    }

}
