package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.DataJpaUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.UserTestData.*;

public class UserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService userService;

    @Autowired
    protected DataJpaUtil dataJpaUtil;

    @Before
    public void setUp() throws Exception {
        userService.evictCache();
        dataJpaUtil.clear2ndLevelHibernateCache();
    }

    @Test
    public void testCreate() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1000, false, new Date(), Collections.singleton(Role.ROLE_USER));
        User created = userService.create(newUser);
        newUser.setId(created.getId());
        MATCHER.assertListEquals(Arrays.asList(ADMIN, newUser, USER), userService.getAll());
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateMailCreate() throws Exception {
        userService.create(new User("Duplicate", "user@gmail.com", "newPass", Role.ROLE_USER));
    }

    @Test
    public void testDelete() throws Exception {
        userService.delete(USER_ID);
        MATCHER.assertListEquals(Collections.singletonList(ADMIN), userService.getAll());
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        userService.delete(1);
    }

    @Test
    public void testGet() throws Exception {
        User user = userService.get(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        userService.get(1);
    }

    @Test
    public void testGetByEmail() throws Exception {
        User user = userService.getByEmail("admin@gmail.com");
        MATCHER.assertEquals(ADMIN, user);
    }

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertListEquals(Arrays.asList(ADMIN, USER), userService.getAll());
    }

    @Test
    public void testUpdate() throws Exception {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(500);
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        userService.update(updated);
        MATCHER.assertEquals(updated, userService.get(USER_ID));
    }

    @Test
    public void testGetWithMeals() throws Exception {
        User user = userService.getWithMeals(ADMIN_ID);
        MATCHER.assertEquals(ADMIN, user);
        MealTestData.MATCHER.assertListEquals(MealTestData.ADMIN_MEALS, user.getMeals());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithMealsNotFound() throws Exception {
        userService.getWithMeals(1);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> userService.create(new User("  ", "mail@gmail.com", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User("User", "  ", "password", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User("User", "mail@gmail.com", "  ", Role.ROLE_USER)), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@gmail.com", "password", 9, true, new Date(), Collections.emptySet())), ConstraintViolationException.class);
        validateRootCause(() -> userService.create(new User(null, "User", "mail@gmail.com", "password", 10001, true, new Date(), Collections.emptySet())), ConstraintViolationException.class);
    }
}