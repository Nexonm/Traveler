package we.itschool.project.traveler.data.api.travelerapi.entityserv;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserServ {

    @SerializedName("ID")
    @Expose
    private Integer id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("secondName")
    @Expose
    private String secondName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("socialContacts")
    @Expose
    private String socialContacts;
    @SerializedName("pathToPhoto")
    @Expose
    private String pathToPhoto;
    @SerializedName("dateOfBirth")
    @Expose
    private String dateOfBirth;
    @SerializedName("male")
    @Expose
    private boolean male;
    @SerializedName("userCards")
    @Expose
    private String userCards;
    @SerializedName("userFavoriteCards")
    @Expose
    private String userFavoriteCards;


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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("firstName");
        sb.append('=');
        sb.append(((this.firstName == null)?"<null>":this.firstName));
        sb.append(',');
        sb.append("secondName");
        sb.append('=');
        sb.append(((this.secondName == null)?"<null>":this.secondName));
        sb.append(',');
        sb.append("email");
        sb.append('=');
        sb.append(((this.email == null)?"<null>":this.email));
        sb.append(',');
        sb.append("phoneNumber");
        sb.append('=');
        sb.append(((this.phoneNumber == null)?"<null>":this.phoneNumber));
        sb.append(',');
        sb.append("socialContacts");
        sb.append('=');
        sb.append(((this.socialContacts == null)?"<null>":this.socialContacts));
        sb.append(',');
        sb.append("pathToPhoto");
        sb.append('=');
        sb.append(((this.pathToPhoto == null)?"<null>":this.pathToPhoto));
        sb.append(',');
        sb.append("dateOfBirth");
        sb.append('=');
        sb.append(((this.dateOfBirth == null)?"<null>":this.dateOfBirth));
        sb.append(',');
        sb.append("male");
        sb.append('=');
        sb.append(this.male);
        sb.append(',');
        sb.append("userCards");
        sb.append('=');
        sb.append(((this.userCards == null)?"<null>":this.userCards));
        sb.append(',');
        sb.append("userFavoriteCards");
        sb.append('=');
        sb.append(((this.userFavoriteCards == null)?"<null>":this.userFavoriteCards));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
