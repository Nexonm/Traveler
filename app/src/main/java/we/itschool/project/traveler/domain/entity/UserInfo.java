package we.itschool.project.traveler.domain.entity;

public class UserInfo {
    private final String firstName;
    private final String secondName;
    private final String email;
    private final String socialContacts;
    private final String pathToPhoto;
    private final String dateOfBirth;

    public UserInfo(
            String firstName,
            String secondName,
            String email,
            String socialContacts,
            String pathToPhoto,
            String dateOfBirth) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.socialContacts = socialContacts;
        this.dateOfBirth = dateOfBirth;
        this.pathToPhoto = pathToPhoto;
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
}
