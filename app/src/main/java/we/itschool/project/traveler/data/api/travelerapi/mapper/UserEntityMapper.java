package we.itschool.project.traveler.data.api.travelerapi.mapper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import we.itschool.project.traveler.data.api.travelerapi.entityserv.CardServ;
import we.itschool.project.traveler.data.api.travelerapi.entityserv.UserServ;
import we.itschool.project.traveler.data.datamodel.UserModelPOJO;
import we.itschool.project.traveler.domain.entity.CardEntity;
import we.itschool.project.traveler.domain.entity.UserEntity;
import we.itschool.project.traveler.domain.entity.UserInfo;


public class UserEntityMapper {

    public static UserEntity toUserEntityFormUserServ(UserServ uServ, boolean withCards) {
        if (withCards)
            return new UserEntity(
                    new UserInfo(
                            uServ.getFirstName() + "",
                            uServ.getSecondName() + "",
                            uServ.getEmail() + "",
                            uServ.getPhoneNumber() + "",
                            uServ.getSocialContacts() + "",
                            uServ.getPathToPhoto() + "",
                            uServ.getDateOfBirth() + "",
                            uServ.isMale(),
                            //making card entities
                            getCardEntityListFromCardServList(
                                    (new Gson()).fromJson(
                                            uServ.getUserCards(),
                                            new TypeToken<ArrayList<CardServ>>() {
                                            }.getType()
                                    )
                            ),
                            //getting favorite cards
                            (new Gson()).fromJson(
                                    uServ.getUserFavoriteCards(),
                                    new TypeToken<ArrayList<Long>>() {
                                    }.getType()
                            )
                    ),
                    uServ.getId()
            );
        else
            return new UserEntity(
                    new UserInfo(
                            uServ.getFirstName() + "",
                            uServ.getSecondName() + "",
                            uServ.getEmail() + "",
                            uServ.getPhoneNumber() + "",
                            uServ.getSocialContacts() + "",
                            uServ.getPathToPhoto() + "",
                            uServ.getDateOfBirth() + "",
                            uServ.isMale(),
                            null,
                            null
                    ),
                    uServ.getId()
            );
    }

    private static List<CardEntity> getCardEntityListFromCardServList(List<CardServ> servs) {
        if (servs != null) {
            List<CardEntity> cards = new ArrayList<>();
            for (CardServ i : servs) {
                //TODO почему false? Нужно заново продумать эту логику
                cards.add(CardEntityMapper.toCardEntityFormCardServ(i, false));
            }
            return cards;
        } else
            return null;
    }

    public static String toUserServFromUserModelPOJO(UserModelPOJO model){
        return (new Gson()).toJson(model);
    }

}
