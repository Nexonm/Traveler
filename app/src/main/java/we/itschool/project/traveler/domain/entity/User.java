package we.itschool.project.traveler.domain.entity;

public class User {
    public static final int UNDEFINED_ID;
    private int id;

    private final UserInfo userInfo;

    static {
        UNDEFINED_ID = -1;
    }

    public User(UserInfo userInfo) {
        this.userInfo = userInfo;
        this.id = UNDEFINED_ID;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public boolean equals(User other) {
        return other.id == this.id;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }
}
