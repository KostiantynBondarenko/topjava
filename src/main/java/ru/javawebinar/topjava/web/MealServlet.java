package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
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
    MealRepository mealRepository = new MemoryMealRepositoryImpl();
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String idStr = request.getParameter("id");
        Integer id = idStr.isEmpty() ? null : Integer.parseInt(idStr);

        LocalDateTime ldt = LocalDateTime.parse(request.getParameter("date"));
        String description = request.getParameter("description");

        int calories = Integer.parseInt(request.getParameter("calories"));

        Meal meal = new Meal(id, ldt, description, calories);

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);

        mealRepository.save(meal);

        response.sendRedirect("meals");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to users");

        String action = request.getParameter("action");

        if (action == null) {
            log.info("forward to mealList get all");
            List<MealWithExceed> mealWithExceed = MealsUtil.getFilteredWithExceeded(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, MemoryMealRepositoryImpl.CALORIES_PER_DAY);
            request.setAttribute("mealWithExceed", mealWithExceed);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } else if (action.equals("delete")) {
            String idString = request.getParameter("id");
            int id = Integer.parseInt(idString);
            log.info("delete id-{}", id);
            mealRepository.delete(id);
            response.sendRedirect("meals");
        } else if (action.equals("edit")) {
            String idString = request.getParameter("id");
            int id = Integer.parseInt(idString);
            Meal meal = mealRepository.get(id);
            log.info("go to edit id-", id);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        } else if (action.equals("create")) {
            Meal meal = new Meal(LocalDateTime.now(), "", 0);
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
        }
    }
}
