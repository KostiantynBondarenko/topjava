package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static int CALORIES_PER_DAY = 2000;
    public static List<Meal> meals;
    static {
        meals = Arrays.asList(
                new Meal(LocalDateTime.of(2017, Month.JULY, 30,10,0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2017, Month.JULY, 30,13,0), "Обед", 1000),
                new Meal(LocalDateTime.of(2017, Month.JULY, 30,20,0), "Ужин", 500),
                new Meal(LocalDateTime.of(2017, Month.JULY, 31,10,0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2017, Month.JULY, 31,13,0), "Обед", 500),
                new Meal(LocalDateTime.of(2017, Month.JULY, 31,20,0), "Ужин", 510)
        );
    }

    public static void main(String[] args) {
        getFilteredWithExceeded(meals, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);
    }

    public static List<MealWithExceed>  getFilteredWithExceeded(List<Meal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> new MealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}