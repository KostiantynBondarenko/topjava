package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

public class UserTestData {
    public static final int ADMIN_ID = 1;
    public static final int USER_ID = 2;

    public static final User ADMIN = new User("Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);
    public static final User USER = new User("User", "user@yandex.ru", "password", Role.ROLE_USER);

    static {
        UserTestData.ADMIN.setId(ADMIN_ID);
        UserTestData.USER.setId(USER_ID);
    }
}