package we.itschool.project.traveler.data.datamodel;

public class CardModelPOJO {

    //fields

    private String city;
    private String country;
    private String fullDescription;
    private String shortDescription;
    private String address;
    private String pathToPhoto;
    private boolean isPaymentFixed;
    private int cost;
    private boolean male;


    //methods



    //constructors

    public CardModelPOJO(
            String city,
            String country,
            String fullDescription,
            String shortDescription,
            String address,
            boolean isPaymentFixed,
            int cost,
            boolean male
    ) {
        this.city = city;
        this.country = country;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.address = address;
        this.isPaymentFixed = isPaymentFixed;
        this.cost = cost;
        this.male = male;
    }


    //getters and setters

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

    public boolean isPaymentFixed() {
        return isPaymentFixed;
    }

    public void setPaymentFixed(boolean paymentFixed) {
        isPaymentFixed = paymentFixed;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
}