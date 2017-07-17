package ru.javawebinar.topjava.repository;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealRepositoryImpl implements MealRepository {
    public static int CALORIES_PER_DAY = 2000;
    private static AtomicInteger counter = new AtomicInteger(1);
    private static ConcurrentMap<Integer, Meal> memoryRepository = new ConcurrentHashMap<>();

    public static List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2017, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2017, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2017, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2017, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2017, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2017, Month.MAY, 31, 20, 0), "Ужин", 510)
    );

    public MemoryMealRepositoryImpl() {
        meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.getAndIncrement());
        }
        return memoryRepository.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        memoryRepository.remove(id);
    }

    @Override
    public Meal get(int id) {
        return memoryRepository.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(memoryRepository.values());
    }
}