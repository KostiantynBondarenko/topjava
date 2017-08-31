package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final BeanMatcher<Meal> MATCHER = BeanMatcher.of(Meal.class);

    public static final int ADMIN_MEAL_ID = START_SEQ + 2;
    public static final int USER_MEAL_ID = START_SEQ + 5;

    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL_ID, of(2017, Month.AUGUST, 17, 10, 0), "Админ завтрак", 500);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL_ID + 1, of(2017, Month.AUGUST, 28, 13, 0), "Админ обед", 1000);
    public static final Meal ADMIN_MEAL3 = new Meal(ADMIN_MEAL_ID + 2, of(2017, Month.AUGUST, 28, 18, 0), "Админ ужин", 1100);

    public static final Meal USER_MEAL1 = new Meal(USER_MEAL_ID, of(2017, Month.AUGUST, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL2 = new Meal(USER_MEAL_ID + 1, of(2017, Month.AUGUST, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL3 = new Meal(USER_MEAL_ID + 2, of(2017, Month.AUGUST, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL4 = new Meal(USER_MEAL_ID + 3, of(2017, Month.AUGUST, 31, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL5 = new Meal(USER_MEAL_ID + 4, of(2017, Month.AUGUST, 31, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL6 = new Meal(USER_MEAL_ID + 5, of(2017, Month.AUGUST, 31, 20, 0), "Ужин", 510);

    public static final List<Meal> ADMIN_MEALS = Arrays.asList(ADMIN_MEAL3, ADMIN_MEAL2, ADMIN_MEAL1);
    public static final List<Meal> USER_MEALS = Arrays.asList(USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1);

    public static Meal getCreated() {
        return new Meal(null, of(2017, Month.SEPTEMBER, 1, 18, 0), "Созданный ужин", 300);
    }

    public static Meal getUpdated() {
        return new Meal(ADMIN_MEAL_ID, ADMIN_MEAL1.getDateTime(), "Обновленный завтрак", 200);
    }
}