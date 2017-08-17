package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void testCreate() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, ADMIN_MEAL3, ADMIN_MEAL2, ADMIN_MEAL1), service.getAll(ADMIN_ID));
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL3, ADMIN_MEAL2), service.getAll(ADMIN_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(ADMIN_MEAL_ID, 1);
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        MATCHER.assertEquals(USER_MEAL1, meal);
    }


    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(ADMIN_MEAL_ID, USER_ID);
    }

    @Test
    public void testGetBetween() throws Exception {
        Collection<Meal> all = service.getBetweenDates(
                LocalDate.of(2017, Month.AUGUST, 20),
                LocalDate.of(2017, Month.AUGUST, 31), ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL3, ADMIN_MEAL2), all);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(USER_MEALS, service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, ADMIN_ID);
        MATCHER.assertEquals(updated, service.get(ADMIN_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + ADMIN_MEAL_ID);
        service.update(ADMIN_MEAL1, USER_ID);
    }

    @Test
    public void testGetWithUser() throws Exception {
        Meal userMeal = service.getWithUser(USER_MEAL_ID, USER_ID);
        MATCHER.assertEquals(USER_MEAL1, userMeal);
        UserTestData.MATCHER.assertEquals(UserTestData.USER, userMeal.getUser());
    }

    @Test(expected = NotFoundException.class)
    public void testGetWithUserNotFound() throws Exception {
        service.getWithUser(ADMIN_MEAL_ID, USER_ID);
    }
}