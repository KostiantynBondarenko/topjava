package ru.javawebinar.topjava.repository.mock;


import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    // Map  userId -> (mealId-> meal)
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public static List<Meal> MEALS_ADMIN = Arrays.asList(
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 28, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 17, 18, 0), "Ужин", 1000)
    );

    public static List<Meal> MEALS_USER = Arrays.asList(
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 31, 20, 0), "Ужин", 510)
    );

    public InMemoryMealRepositoryImpl() {
        MEALS_ADMIN.forEach(meal -> this.save(meal, InMemoryUserRepositoryImpl.ADMIN_ID));
        MEALS_USER.forEach(meal -> this.save(meal, InMemoryUserRepositoryImpl.USER_ID));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllAsStream(userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllAsStream(userId).collect(Collectors.toList());
    }

    private Stream<Meal> getAllAsStream(int userId) {
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ?
                Stream.empty() :
                meals.values().stream()
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed());
    }
}
