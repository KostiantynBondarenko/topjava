package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.UserTestData.*;

public abstract class AbstractUserServiceTest extends AbstractServiceTest {

    @Autowired
    protected UserService userService;

    @Before
    public void setUp() throws Exception {
        userService.evictCache();
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
}