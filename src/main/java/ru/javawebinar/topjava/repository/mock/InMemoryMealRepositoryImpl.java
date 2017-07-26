package ru.javawebinar.topjava.repository.mock;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private static Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private static AtomicInteger counter = new AtomicInteger(0);

    public static List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2017, Month.AUGUST, 31, 20, 0), "Ужин", 510)
    );

    public InMemoryMealRepositoryImpl() {
        MEALS.forEach(meal -> this.save(meal, 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {} for User {}", meal, userId);
        Objects.requireNonNull(meal);
        Integer mealId = meal.getId();
        if (meal.isNew()) {
            mealId = counter.incrementAndGet();
            meal.setId(mealId);
        } else if (get(mealId, userId) == null) {
            return null;
        }
        Map<Integer, Meal> meals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        meals.put(mealId, meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {} for User {}", id, userId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals != null && meals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {} for User {}", id, userId);
        Map<Integer, Meal> meals = repository.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public Collection<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        log.info("getBetween {} - {} for User {}", startDateTime, endDateTime, userId);
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);
        return getAllAsStream(userId)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll for User {}", userId);
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
