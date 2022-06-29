package traveler.module.data.repositoryImpl;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import traveler.module.data.repositoryImpl.answer.AnswerUserAndString;
import traveler.module.data.travelerapi.entityserv.CardServ;
import traveler.module.data.travelerapi.errors.UserNetAnswers;
import traveler.module.data.travelerapi.mapper.CardEntityMapper;
import traveler.module.data.travelerapi.service.APIServiceCard;
import traveler.module.data.travelerapi.service.APIServiceTravelerConstructor;
import traveler.module.data.usecaseinterface.user.UserAddCardToFavoritesUCI;
import traveler.module.data.usecaseinterface.user.UserAddPhotoUCI;
import traveler.module.data.usecaseinterface.user.UserCreateNewCardUCI;
import traveler.module.data.usecaseinterface.user.UserLoginUCI;
import traveler.module.data.usecaseinterface.user.UserRegUCI;
import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.entity.UserEntity;
import traveler.module.domain.repository.UserDomainRepository;

public class UserRepositoryImpl implements UserDomainRepository {

    private static UserEntity userMain;
    private final static ArrayList<CardEntity> userFavCards;

    private final UserLoginUCI loginUCI;
    private final UserRegUCI regUCI;
    private final UserAddPhotoUCI addPhotoUCI;
    private final UserCreateNewCardUCI createNewCardUCI;
    private final UserAddCardToFavoritesUCI addCardToFavoritesUCI;

    static {
        userMain = null;
        userFavCards = new ArrayList<>();
    }

    public UserRepositoryImpl(
            UserLoginUCI loginUCI,
            UserRegUCI regUCI,
            UserAddPhotoUCI addPhotoUCI,
            UserCreateNewCardUCI createNewCardUCI,
            UserAddCardToFavoritesUCI addCardToFavoritesUCI
    ) {
        this.loginUCI = loginUCI;
        this.regUCI = regUCI;
        this.addPhotoUCI = addPhotoUCI;
        this.createNewCardUCI = createNewCardUCI;
        this.addCardToFavoritesUCI = addCardToFavoritesUCI;
    }

    @Override
    public UserEntity getMainUserForThisApp() {
        return userMain;
    }

    public static UserEntity getUserMain() {
        return userMain;
    }

    public static void setUserMain(UserEntity user) {
        if (user != null)
            userMain = user;
    }

    @Override
    public String login(String login, String pass) {
        AnswerUserAndString ans = loginUCI.login(login, pass);
        setUserMain(ans.getUser());
        //another action
        if (userMain!=null)
        for (long id : userMain.getUserInfo().getUserFavoritesCards()) {
            uploadCardToFavs(id);
        }
        return ans.getAnswer();
    }

    @Override
    public ArrayList<CardEntity> getUserCards() {
        return (ArrayList<CardEntity>) (userMain.getUserInfo().getUserCards());
    }

    @Override
    public ArrayList<CardEntity> getUserFavoritesCards(long id) {
        return userFavCards;
    }

    @Override
    public void addCardToFavorites(long cid) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Long> list = addCardToFavoritesUCI.addCardToFavorites(userMain.get_id(), cid);
                userMain.getUserInfo().setUserFavoritesCards(list);
                chekUserFavs(list);
            }
        }).start();
    }

    @Override
    public void createNewCard(CardEntity createdCard) {
        createNewCardUCI.createNewCard(createdCard, userMain.get_id());
        addCardToUserCards(createdCard);
    }

    @Override
    public void addPhoto(String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ans = addPhotoUCI.addPhoto(path, getUserMain().get_id());
                if (!UserNetAnswers.userOtherError.equals(ans)) {
                    userMain.getUserInfo().setPathToPhoto(ans);
                }
            }
        }).start();

    }

    @Override
    public String regNew(UserEntity newUser, String pass) {
        AnswerUserAndString ans = regUCI.regNew(newUser, pass);
        setUserMain(ans.getUser());
        return ans.getAnswer();
    }

    @Override
    public void setInterests(String interests) {
        //TODO send server request to set Interests
    }

    @Override
    public void setDescription(String desc) {
        //TODO send server request to ser Description
    }

    private void addCardToUserCards(CardEntity card) {
        userMain.getUserInfo().getUserCards().add(card);
    }

    private void chekUserFavs(ArrayList<Long> list) {
        uploadCardToFavs(
                list.get(
                        list.size() - 1
                )
        );
    }

    private void uploadCardToFavs(long cid) {
        APIServiceCard service = APIServiceTravelerConstructor.CreateService(APIServiceCard.class);
        Call<String> call = service.getOneCardById(cid);
        //Log.v(MainActivity.TAG, "start catching the answer");
        call.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                if (response.body() != null) {
                    String str = response.body().toString();
//                    Log.v("retrofitLogger", "card.toString():" + str);
                    //add card to userMain card list
                    addCardToUserFavsData(CardEntityMapper.toCardEntityFormCardServ(
                            (new Gson()).fromJson(str, CardServ.class)
                            , true));

                } else {
//                    Log.v("retrofitLogger", "null response.body on loadDataRetrofit");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<String> call, Throwable t) {
//                Log.v("retrofitLogger", "some error!!! on failure id:" + id +
//                        " t:" + t.getMessage());
            }
        });
    }

    private void addCardToUserFavsData(CardEntity card) {
        userFavCards.add(card);
    }
}
