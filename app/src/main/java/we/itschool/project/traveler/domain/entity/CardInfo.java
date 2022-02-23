package we.itschool.project.traveler.domain.entity;

public class CardInfo {
    private final Person person;
    private final String city;
    private final String fullDescription;
    private final String shortDescription;
    private final String address;
    private final String pathToPhoto;

    public CardInfo(
            Person person,
            String city,
            String fullDescription,
            String shortDescription,
            String address,
            String pathToPhoto) {
        this.person = person;
        this.city = city;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.address = address;
        this.pathToPhoto = pathToPhoto;
    }

    public Person getPerson() {
        return person;
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
}
