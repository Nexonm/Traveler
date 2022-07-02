package traveler.module.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class UserInfo {
    private final String firstName;
    private final String secondName;
    private final String email;
    private String phoneNumber;
    private String socialContacts;
    private String pathToPhoto;
    private final String dateOfBirth;
    private final boolean isMale;
    private final String interests;
    private final String characteristics;
    private List<CardEntity> userCards;
    private List<Long> userFavoritesCards;


    public UserInfo(
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String socialContacts,
            String pathToPhoto,
            String dateOfBirth,
            boolean isMale,
            String interests,
            String characteristics,
            List<CardEntity> userCards,
            List<Long> userFavoritesCards) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.socialContacts = socialContacts;
        this.dateOfBirth = dateOfBirth;
        this.pathToPhoto = pathToPhoto;
        this.isMale = isMale;
        this.interests = interests;
        this.characteristics = characteristics;
        this.userCards = userCards;
        this.userFavoritesCards = userFavoritesCards;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public String getSocialContacts() {
        return socialContacts;
    }

    public void setSocialContacts(String socialContacts) {
        this.socialContacts = socialContacts;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isMale() {
        return isMale;
    }

    public String getInterests() {
        return interests;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public List<CardEntity> getUserCards() {
        return userCards;
    }

    public void setUserCards(ArrayList<CardEntity> cards){
        this.userCards = cards;
    }

    public List<Long> getUserFavoritesCards() {
        return userFavoritesCards;
    }

    public void setUserFavoritesCards(List<Long> userFavoritesCards) {
        this.userFavoritesCards = userFavoritesCards;
    }
}
