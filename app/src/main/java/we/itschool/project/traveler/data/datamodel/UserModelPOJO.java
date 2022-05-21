package we.itschool.project.traveler.data.datamodel;

public class UserModelPOJO {
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
    private String socialContacts;
    private String pathToPhoto;
    private String dateOfBirth;
    private boolean male;
    private String interests;
    private String characteristics;
    private String userCards;
    private String userFavoriteCards;
    private String password;

    //constructors

    public UserModelPOJO(
            String password,
            String firstName,
            String secondName,
            String email,
            String dateOfBirth
    ) {
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public UserModelPOJO(
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String socialContacts,
            String dateOfBirth,
            boolean male
    ) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.socialContacts = socialContacts;
        this.dateOfBirth = dateOfBirth;
        this.male = male;
        this.pathToPhoto = "null path";
        this.userCards = "[]";
        this.userFavoriteCards = "[]";
    }


    //getters and setters


    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public String getUserCards() {
        return userCards;
    }

    public void setUserCards(String userCards) {
        this.userCards = userCards;
    }


    public String getUserFavoriteCards() {
        return userFavoriteCards;
    }

    public void setUserFavoriteCards(String userFavoriteCards) {
        this.userFavoriteCards = userFavoriteCards;
    }

}
