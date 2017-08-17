package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collections;

import static ru.javawebinar.topjava.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Before
    public void setUp() throws Exception {
        service.evictCache();
    }

    @Test
    public void testCreate() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1000, false, Collections.singleton(Role.ROLE_USER));
        User created = service.create(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, newUser, USER), service.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailCreate() throws Exception {
        service.create(new User("Duplicate", "user@gmail.ru", "newPass", Role.ROLE_USER));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN), service.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        User user = service.get(USER_ID);
        MATCHER.assertEquals(USER, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = service.getByEmail("user@gmail.ru");
        MATCHER.assertEquals(USER, user);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN, USER), service.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(500);
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(USER_ID));
    }

    @Test
    public void testGetWithMeals() throws Exception {
        User user = service.getWithMeals(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.ADMIN_MEALS, user.getMeals());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithMealsNotFound() throws Exception {
        service.getWithMeals(1);
    }
}