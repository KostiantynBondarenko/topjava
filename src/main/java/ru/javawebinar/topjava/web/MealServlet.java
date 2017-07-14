package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final org.slf4j.Logger log = getLogger(UserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");
        List<MealWithExceed> mealWithExceed = MealsUtil.getFilteredWithExceeded(
                MealsUtil.meals, LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);

        request.setAttribute("mealWithExceed", mealWithExceed);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);

    }
}
