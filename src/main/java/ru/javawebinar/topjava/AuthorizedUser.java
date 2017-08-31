package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class AuthorizedUser {
    private static int ID = AbstractBaseEntity.START_SEQ;

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        AuthorizedUser.ID = ID;
    }

    public static int getCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}