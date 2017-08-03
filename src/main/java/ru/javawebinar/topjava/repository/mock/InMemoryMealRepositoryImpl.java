package ru.javawebinar.topjava.repository.mock;


import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.ADMIN_ID;
import static ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl.USER_ID;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    public static List<Meal> ADMIN_MEALS = Arrays.asList(
            new Meal(of(2017, Month.AUGUST, 17, 10, 0), "Админ завтрак", 500),
            new Meal(of(2017, Month.AUGUST, 28, 13, 0), "Админ обед", 1000),
            new Meal(of(2017, Month.AUGUST, 28, 18, 0), "Админ ужин", 1100)
    );

    public static List<Meal> USER_MEALS = Arrays.asList(
            new Meal(of(2017, Month.AUGUST, 30, 10, 0), "Завтрак", 500),
            new Meal(of(2017, Month.AUGUST, 30, 13, 0), "Обед", 1000),
            new Meal(of(2017, Month.AUGUST, 30, 20, 0), "Ужин", 500),
            new Meal(of(2017, Month.AUGUST, 31, 10, 0), "Завтрак", 500),
            new Meal(of(2017, Month.AUGUST, 31, 13, 0), "Обед", 1000),
            new Meal(of(2017, Month.AUGUST, 31, 20, 0), "Ужин", 510)
    );

    // Map  userId -> (mealId-> meal)
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public InMemoryMealRepositoryImpl() {
        ADMIN_MEALS.forEach(meal -> this.save(meal, ADMIN_ID));
        USER_MEALS.forEach(meal -> this.save(meal, USER_ID));
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