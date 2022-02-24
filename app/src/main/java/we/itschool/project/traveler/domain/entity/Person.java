package we.itschool.project.traveler.domain.entity;

public class Person {
    public static final int UNDEFINED_ID;
    private int id;

    private final PersonInfo personInfo;

    static {
        UNDEFINED_ID = -1;
    }

    public Person(PersonInfo personInfo) {
        this.personInfo = personInfo;
        this.id = UNDEFINED_ID;
    }

    public PersonInfo getPersonInfo() {
        return personInfo;
    }

    public boolean equals(Person other) {
        return other.id == this.id;
    }

    public int get_id() {
        return id;
    }

    public void set_id(int id) {
        this.id = id;
    }
}
