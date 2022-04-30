package we.itschool.project.traveler.domain.entity;

public class CardInfo {
    private final UserEntity user;
    private final String city;
    private final String country;
    private final String fullDescription;
    private final String shortDescription;
    private final String address;
    private final String pathToPhoto;
    private final int cost;
    private final boolean isMale;
    private final boolean paymentFixed;

    public CardInfo(
            UserEntity user,
            String city,
            String country,
            String fullDescription,
            String shortDescription,
            String address,
            String pathToPhoto,
            int cost,
            boolean isMale,
            boolean paymentFixed) {
        this.user = user;
        this.city = city;
        this.country = country;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.address = address;
        this.pathToPhoto = pathToPhoto;
        this.cost = cost;
        this.isMale = isMale;
        this.paymentFixed = paymentFixed;
    }

    public UserEntity getUser() {
        return user;
    }

    public String getCity() {
        return city;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getAddress() {
        return address;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public String getCountry() {
        return country;
    }

    public int getCost() {
        return cost;
    }

    public boolean isMale() {
        return isMale;
    }

    public boolean isPaymentFixed() {
        return paymentFixed;
    }
}
