package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.MemoryMealRepositoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final org.slf4j.Logger log = getLogger(MealServlet.class);
    MealRepository mealRepository = new MemoryMealRepositoryImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String idString = request.getParameter("id");
        Integer id = idString.isEmpty() ? null : Integer.parseInt(idString);
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(id, localDateTime, description, calories);
        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        mealRepository.save(meal);
        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            log.info("forward to meals get all");
            List<MealWithExceed> mealsWithExceed = MealsUtil.getFilteredWithExceeded(
                    mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, MemoryMealRepositoryImpl.CALORIES_PER_DAY);
            request.setAttribute("mealsWithExceed", mealsWithExceed);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("create")) {
            LocalDateTime localDateTime = LocalDateTime.now();
            LocalDateTime localDateTimeWithoutSecond = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.of(localDateTime.getHour(), localDateTime.getMinute()));
            Meal meal = new Meal(localDateTimeWithoutSecond, "", 0);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        } else if (action.equals("edit")) {
            String idString = request.getParameter("id");
            int id = Integer.parseInt(idString);
            Meal meal = mealRepository.get(id);
            log.info("go to mealEdit id ", id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            String idString = request.getParameter("id");
            int id = Integer.parseInt(idString);
            log.info("delete id {}", id);
            mealRepository.delete(id);
            response.sendRedirect("meals");
        }
    }
}
