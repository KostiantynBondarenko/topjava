package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public abstract class AbstractMealServiceTest extends AbstractServiceTest {

    @Autowired
    protected MealService mealService;

    @Test
    public void testCreate() throws Exception {
        Meal newMeal = getCreated();
        Meal created = mealService.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, ADMIN_MEAL3, ADMIN_MEAL2, ADMIN_MEAL1), mealService.getAll(ADMIN_ID));
    }

    @Test
    public void testDelete() throws Exception {
        mealService.delete(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL3, ADMIN_MEAL2), mealService.getAll(ADMIN_ID));
    }

    @Test
    public void testDeleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        mealService.delete(ADMIN_MEAL_ID, 1);
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = mealService.get(USER_MEAL_ID, USER_ID);
        MATCHER.assertEquals(USER_MEAL1, meal);
    }


    @Test
    public void testGetNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        mealService.get(ADMIN_MEAL_ID, USER_ID);
    }

    @Test
    public void testGetBetween() throws Exception {
        Collection<Meal> all = mealService.getBetweenDates(
                LocalDate.of(2017, Month.AUGUST, 20),
                LocalDate.of(2017, Month.AUGUST, 31), ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL3, ADMIN_MEAL2), all);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(USER_MEALS, mealService.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        mealService.update(updated, ADMIN_ID);
        MATCHER.assertEquals(updated, mealService.get(ADMIN_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void testUpdateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + ADMIN_MEAL_ID);
        mealService.update(ADMIN_MEAL1, USER_ID);
    }

    @Test
    public void testValidation() throws Exception {
        validateRootCause(() -> mealService.create(new Meal(null, of(2017, Month.AUGUST, 1, 18, 0), "  ", 300), ADMIN_ID), ConstraintViolationException.class);
        validateRootCause(() -> mealService.create(new Meal(null, null, "Description", 300), ADMIN_ID), ConstraintViolationException.class);
        validateRootCause(() -> mealService.create(new Meal(null, of(2017, Month.AUGUST, 1, 18, 0), "Description", 9), ADMIN_ID), ConstraintViolationException.class);
        validateRootCause(() -> mealService.create(new Meal(null, of(2017, Month.AUGUST, 1, 18, 0), "Description", 5001), ADMIN_ID), ConstraintViolationException.class);
    }
}