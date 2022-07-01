package traveler.module.data.travelerapi.errors;

public class UserNetAnswers {
    //errors produced by client side
    public final static String userOtherError = "При выполнении запроса произошла ошибка";

    //errors from server
    public final static String userDoesNotExistException = "Пользователя не существует";
    public final static String userIncorrectPasswordException = "Пароль не соответствует, вход невозможен";
    public final static String userAlreadyExists = "Пользователь с таким email уже существует";
    public final static String userDataFormatException = "Данные не соответствуют ожидаемому формату";
    public final static String userNoDateOfBirthException = "Регистрация невозможна: не предоставлены данные о дате рождения";;
    public final static String userDataNoEmailException = "Регистрация невозможна: не предоставлены данные email";
    public final static String userDataNoFirstNameException = "Регистрация невозможна: не предоставлены данные об ИМЕНИ";
    public final static String userDataNoSecondNameException = "Регистрация невозможна: не предоставлены данные о ФАМИЛИИ";
    public final static String userDataCardCreatedByAnotherUser = "Карта с таким id не может быть удалена: принадлежит другому пользователю";
    public final static String userPhotoLoadingException = "Ошибка";

    //successful actions
    public final static String userSuccessLogin = "Вход выполнен";
    public final static String userSuccessRegistration = "Регистрация выполнена";
}
