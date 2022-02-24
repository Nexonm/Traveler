package we.itschool.project.traveler.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;

import we.itschool.project.traveler.R;
import we.itschool.project.traveler.domain.entity.Card;
import we.itschool.project.traveler.domain.entity.CardInfo;
import we.itschool.project.traveler.domain.entity.Person;
import we.itschool.project.traveler.domain.entity.PersonInfo;

public class NewData {

    public static String genPathToPhoto() {
        Field[] drawablesFields = R.drawable.class.getFields();
        ArrayList<String> imageName = new ArrayList<>();
        for (Field field : drawablesFields)
            if (field.getName().length() == 4)
                imageName.add(field.getName());
        // Из имени ресурса получить идентификатор
        // String mDrawableName = "name"; // файл name.png в папке drawable
        // int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
        return imageName.get((int) (Math.random() * imageName.size()));
    }

    public static String genCity() {
        String[] cities = {"Лондон", "Париж", "Нью-Йорк", "Тюмень", "Сургут",
                "Ярославль", "Владивосток", "Казань", "Сочи",
                "Санкт-Петербург", "Домодедово", "Краснодар",
                "Калининград", "Уфа", "Екатеринбург", "Новосибирск",
                "Геленджик", "Москва", "Дубай", "Токио", "Сингапур",
                "Лос-Анджелес", "Барселона", "Мадрид", "Рим", "Доха",
                "Чикаго", "Абу-Даби", "Сан-Франциско"};
        return cities[(int) (Math.random() * cities.length)];
    }

    public static String genFirstName() {
        String[] names = {"Беатриса", "Ванесса", "Джульетта", "Изабелла", "Розалина",
                "Камилла", "Таня", "Август", "Кай",
                "Елисей", "Сергей", "Алексей",
                "Никита", "Снежана", "Надежда", "Матвей",
                "Итто", "Синдзи", "Рей", "Сара", "Аска", "Харухи", "Юмэко", "Чуя", "Кагуя"};
        return names[(int) (Math.random() * names.length)];
    }

    public static String genSecondName() {
        String[] names = {"Аратаки", "Кинг", "Грей", "Адамс", "Хейз",
                "Смит", "Кларк", "Куджо", "Аянами",
                "Икари", "Тейлор", "Джонсон",
                "Такахаси", "Ягами", "Миямото", "Учиха",
                "Джостар", "Адзусагава", "Накахара", "Камадо", "де Блуа", "Шмидт", "Рихтер", "Кляйн"};
        return names[(int) (Math.random() * names.length)];
    }

    public static String genShortDescription() {
        return genDescriptionBasic(8, 10);
    }

    public static String genFullDescription() {
        return genDescriptionBasic(5, 100);
    }

    private static String genDescriptionBasic(int length, int words) {
        String alphabetInUpperCase = "QWERTYUIOPASDFGHJKLZXCVBNM";
        String alphabetInLowerCase = "qwertyuiopasdfghjklzxcvbnm";
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < words; j++) {
            result.append(
                    alphabetInUpperCase.charAt(
                            (int) (Math.random() * 26)
                    )
            );
            for (int i = 0; i < length - 1; i++) {
                result.append(
                        alphabetInLowerCase.charAt(
                                (int) (Math.random() * 26)
                        )
                );
            }
            result.append(" ");
        }
        return result.toString();
    }

    private static String genNumber(int length) {
        String digits = "0123456789";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++)
            result.append(
                    digits.charAt(
                            (int) (Math.random() * 10)
                    )
            );
        return result.toString();
    }

    public static String genPhoneNumber() {
        return "+79" + genNumber(9);
    }

    public static String genEmail() {
        return genCity() + "_toVisit@gmail.com";
    }

    public static String genDate() {
        return "24.02.2000";
    }

    public static String genAddress() {
        return "City " + genCity()
                + ", Street " + genNumber(2)
                + ", Home " + genNumber(2);
    }

    public static Card newCard() {
        return
                new Card(
                        new CardInfo(
                                new Person(
                                        new PersonInfo(
                                                genFirstName(),
                                                genSecondName(),
                                                genEmail(),
                                                genPhoneNumber(),
                                                genDate(),
                                                genPathToPhoto()
                                        )
                                ),
                                genCity(),
                                genFullDescription(),
                                genShortDescription(),
                                genAddress(),
                                genPathToPhoto()
                        )
                );
    }


}
