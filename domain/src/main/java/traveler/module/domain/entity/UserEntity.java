package traveler.module.domain.entity;

public class UserEntity {
    public static final int UNDEFINED_ID;
    private int id;

    private final UserInfo userInfo;

    static {
        UNDEFINED_ID = -1;
    }

    public UserEntity(UserInfo userInfo, int id) {
        this.userInfo = userInfo;
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public boolean equals(UserEntity other) {
        return other.id == this.id;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }
}
