package traveler.module.data.travelerapi.errors;

public class ErrorsUser {
    public final static String userDoesNotExistException = "Пользователя не существует";
    public final static String UserIncorrectPasswordException = "Пароль не соответствует, вход невозможен";
    public final static String userDataFormatException = "Данные не соответствуют ожидаемому формату";
    public final static String userNoDateOfBirthException = "Регистрация невозможна: не предоставлены данные о дате рождения";;
    public final static String userDataNoEmailException = "Регистрация невозможна: не предоставлены данные email";
    public final static String userDataNoFirstNameException = "Регистрация невозможна: не предоставлены данные об ИМЕНИ";
    public final static String userDataNoSecondNameException = "Регистрация невозможна: не предоставлены данные о ФАМИЛИИ";
    public final static String userDoesnotExists = "Пользователя не существует";
}
