package traveler.module.data.travelerapi;

public class APIConfigTraveler {
    //main server ip, written by Traveler developers team
    public static final String HOST_URL = "http://84.201.141.185:8080/";
    //path to get card
    public static final String STORAGE_CARD_PHOTO_METHOD = HOST_URL+"storage/image-card?cid="; //needs card id
    public static final String STORAGE_USER_PHOTO_METHOD = HOST_URL+"storage/image-user?uid="; //needs card id
}
