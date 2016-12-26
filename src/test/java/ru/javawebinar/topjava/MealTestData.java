package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Objects;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int ADMIN_MEAL_ID1 = USER_ID + 8;
    public static final int ADMIN_MEAL_ID2 = USER_ID + 9;
    public static final int USER_MEAL_ID = USER_ID + 2;

    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID1, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID2, LocalDateTime.of(2015, Month.JUNE, 3, 21, 0), "Админ ужин", 1500);
    public static final Meal USER_MEAL = new Meal(USER_MEAL_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);

    public static final LocalDateTime START_DATE_TIME = LocalDateTime.of(2015, Month.JUNE, 1, 14, 0);
    public static final LocalDateTime END_DATE_TIME = LocalDateTime.of(2015, Month.JUNE, 3, 21, 0);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();
}
