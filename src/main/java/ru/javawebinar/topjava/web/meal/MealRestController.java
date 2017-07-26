package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        meal.setId(null);
        int userId = AuthorizedUser.getID();
        log.info("create {} for User {}", meal, userId);
        return service.save(meal, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.getID();
        log.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public Meal get(int id) {
        int userId = AuthorizedUser.getID();
        log.info("get meal {} for User {}", id, userId);
        return service.get(id, userId);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.getID();
        log.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, userId);

        return MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(startDate != null ? startDate : LocalDate.MIN, endDate != null ? endDate : LocalDate.MAX, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
    }

    public void update(Meal meal, int id) {
        meal.setId(id);
        int userId = AuthorizedUser.getID();
        log.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }

    public List<MealWithExceed> getAll() {
        int userId = AuthorizedUser.getID();
        log.info("getAll for User {}", userId);
        return MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay());
    }
}