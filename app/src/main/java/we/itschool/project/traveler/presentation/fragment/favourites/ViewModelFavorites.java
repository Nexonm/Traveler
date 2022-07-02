package we.itschool.project.traveler.presentation.fragment.favourites;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.usecases.user.UserGetMainUseCase;
import traveler.module.domain.usecases.user.UserGetUserFavoritesCardsUseCase;
import we.itschool.project.traveler.app.AppStart;

public class ViewModelFavorites extends ViewModel {

    private final MutableLiveData<ArrayList<CardEntity>> cardsLiveDataList = new MutableLiveData<>();

    private final UserGetUserFavoritesCardsUseCase getAllUC = AppStart.uGetUserFavesUC;
    private final UserGetMainUseCase getUserUC = AppStart.uGetMainUserUC;

    protected MutableLiveData<ArrayList<CardEntity>> getCardList() {
        cardsLiveDataList.postValue(getAllUC.getUserFavoritesCards(getUserUC.getMainUser().get_id()));
        return cardsLiveDataList;
    }
}
