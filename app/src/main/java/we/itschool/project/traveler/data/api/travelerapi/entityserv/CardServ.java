package we.itschool.project.traveler.data.api.travelerapi.entityserv;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardServ {

    @SerializedName("ID")
    @Expose
    private int id;
    @SerializedName("user")
    @Expose
    private UserServ user;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("fullDescription")
    @Expose
    private String fullDescription;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pathToPhoto")
    @Expose
    private String pathToPhoto;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("male")
    @Expose
    private Boolean male;
    @SerializedName("isPaymentFixed")
    @Expose
    private Boolean paymentFixed;

    public UserServ getUser() {
        return user;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getMale() {
        return male;
    }

    public Boolean getPaymentFixed() {
        return paymentFixed;
    }

    public void setUser(UserServ user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Boolean isMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean isPaymentFixed() {
        return paymentFixed;
    }

    public void setPaymentFixed(Boolean paymentFixed) {
        this.paymentFixed = paymentFixed;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CardServ.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("user");
        sb.append('=');
        sb.append(((this.user == null)?"<null>":this.user));
        sb.append(',');
        sb.append("city");
        sb.append('=');
        sb.append(((this.city == null)?"<null>":this.city));
        sb.append(',');
        sb.append("country");
        sb.append('=');
        sb.append(((this.country == null)?"<null>":this.country));
        sb.append(',');
        sb.append("fullDescription");
        sb.append('=');
        sb.append(((this.fullDescription == null)?"<null>":this.fullDescription));
        sb.append(',');
        sb.append("shortDescription");
        sb.append('=');
        sb.append(((this.shortDescription == null)?"<null>":this.shortDescription));
        sb.append(',');
        sb.append("address");
        sb.append('=');
        sb.append(((this.address == null)?"<null>":this.address));
        sb.append(',');
        sb.append("pathToPhoto");
        sb.append('=');
        sb.append(((this.pathToPhoto == null)?"<null>":this.pathToPhoto));
        sb.append(',');
        sb.append("cost");
        sb.append('=');
        sb.append(((this.cost == null)?"<null>":this.cost));
        sb.append(',');
        sb.append("male");
        sb.append('=');
        sb.append(((this.male == null)?"<null>":this.male));
        sb.append(',');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == 0)?"<null>":this.id));
        sb.append(',');
        sb.append("paymentFixed");
        sb.append('=');
        sb.append(((this.paymentFixed == null)?"<null>":this.paymentFixed));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}