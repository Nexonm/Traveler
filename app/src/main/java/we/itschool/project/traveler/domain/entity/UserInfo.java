package we.itschool.project.traveler.domain.entity;

import java.util.List;

public class UserInfo {private final String firstName;
    private final String secondName;
    private final String email;
    private final String phoneNumber;
    private final String socialContacts;
    private final String pathToPhoto;
    private final String dateOfBirth;
    private final boolean isMale;
    private final List<CardEntity> userCards;
    private final List<Long> userFavoritesCards;


    public UserInfo(
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String socialContacts,
            String pathToPhoto,
            String dateOfBirth,
            boolean isMale,
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

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isMale() {
        return isMale;
    }

    public List<CardEntity> getUserCards() {
        return userCards;
    }

    public List<Long> getUserFavoritesCards() {
        return userFavoritesCards;
    }
}
