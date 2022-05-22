package we.itschool.project.traveler.presentation.fragment.favourites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import we.itschool.project.traveler.app.AppStart;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.usecases.card.CardAddNewFavoriteFromServerUseCase;
import we.itschool.project.traveler.domain.usecases.card.CardGetAllUserFavoriteCardsUseCase;

public class ViewModelFavorites extends ViewModel {

    private MutableLiveData<ArrayList<CardEntity>> cardsLiveDataList;

    private final CardAddNewFavoriteFromServerUseCase addNewUC = AppStart.cardAddNewFavCardFromServ;
    CardGetAllUserFavoriteCardsUseCase getAllUC = AppStart.cardGetUserFavsUC;

    protected MutableLiveData<ArrayList<CardEntity>> getCardList(){
        cardsLiveDataList = getAllUC.getUserFavCards();
        return cardsLiveDataList;
    }

    protected void addCard(long id){
        addNewUC.cardAddNewFavsFromServ(id);
    }
}
