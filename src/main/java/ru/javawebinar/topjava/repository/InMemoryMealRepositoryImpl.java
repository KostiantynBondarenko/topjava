package ru.javawebinar.topjava.repository;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private static Map<Integer, Meal> repository  = new ConcurrentHashMap<>();
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
        MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values();
    }
}