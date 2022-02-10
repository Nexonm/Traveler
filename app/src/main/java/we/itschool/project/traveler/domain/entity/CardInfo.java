package we.itschool.project.traveler.domain.entity;

public class CardInfo {

    private final String city;
    private final String description;
    private final String email;
    private final String phone;
    private final String address;
    private final String pathToPhoto;

    public CardInfo(
            String city,
            String description,
            String email,
            String phone,
            String address,
            String pathToPhoto) {
        this.city = city;
        this.description = description;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.pathToPhoto = pathToPhoto;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }
}
