package traveler.module.data.travelerapi.mapper;


import com.google.gson.Gson;

import traveler.module.data.travelerapi.entityserv.CardServ;
import traveler.module.data.datamodel.CardModelPOJO;
import traveler.module.domain.entity.CardEntity;
import traveler.module.domain.entity.CardInfo;

public class CardEntityMapper {
    /**
     *
     * @param cServ
     * @param withUser in case you want to get user with his cards
     * @return
     */
    public static CardEntity toCardEntityFormCardServ(CardServ cServ, boolean withUser) {
        return new CardEntity(
                new CardInfo(
                        (withUser?UserEntityMapper.toUserEntityFormUserServ(cServ.getUser(), false):null),
                        cServ.getCity() + "",
                        cServ.getCountry() + "",
                        cServ.getFullDescription() + "",
                        cServ.getShortDescription() + "",
                        cServ.getAddress() + "",
                        cServ.getPathToPhoto() + "",
                        cServ.getHashtag()
                        ),
                cServ.getId()
        );
    }

    //needs to add card to server
    public static String toCardServFromCardEntity(CardEntity entity){
        return (new Gson()).toJson(
                new CardModelPOJO(
                        entity.getCardInfo().getCity()+"",
                        entity.getCardInfo().getCountry()+"",
                        entity.getCardInfo().getFullDescription()+"",
                        entity.getCardInfo().getShortDescription()+"",
                        entity.getCardInfo().getAddress()+"",
                        entity.getCardInfo().getPathToPhoto(),
                        entity.getCardInfo().getHashtag()
                )
        );
    }

    //from model
    public static String toCardServFromCardModelPOJO(CardModelPOJO model){
        return (new Gson()).toJson(model);
    }
}
