package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void testSave() throws Exception {
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

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(ADMIN_MEAL_ID, 1);
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        MATCHER.assertEquals(USER_MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
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

    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        service.update(ADMIN_MEAL1, USER_ID);
    }
}