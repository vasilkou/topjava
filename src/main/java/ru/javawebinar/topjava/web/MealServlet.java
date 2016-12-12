package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.GenericDao;
import ru.javawebinar.topjava.dao.MealDaoRepositoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.DateTimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static java.lang.Long.parseLong;
import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);
    private GenericDao<Meal> mealDao;

    public MealServlet() {
        mealDao = new MealDaoRepositoryImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");

        if (action == null) {
            forward = "/meals.jsp";
            request.setAttribute("meals", getListOfFilteredMeals());
        } else if (action.equalsIgnoreCase("delete")) {
            forward = "/meals.jsp";
            mealDao.delete(parseLong(request.getParameter("id")));
            request.setAttribute("meals", getListOfFilteredMeals());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = "/editMeal.jsp";
            Meal meal = mealDao.getById(parseLong(request.getParameter("id")));
            request.setAttribute("mealToEdit", meal);
        } else if (action.equalsIgnoreCase("add")) {
            forward = "/addMeal.jsp";
        }

        request.getRequestDispatcher(forward).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        Meal meal = new Meal(
                null,
                DateTimeUtil.parseLocalDateTime(request.getParameter("dateTime"), "yyyy-MM-dd'T'HH:mm"),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        );

        String idParam = request.getParameter("id");
        if (idParam == null) {
            mealDao.add(meal);
        } else {
            meal.setId(parseLong(idParam));
            mealDao.update(meal);
        }

        request.setAttribute("meals", getListOfFilteredMeals());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private List<MealWithExceed> getListOfFilteredMeals() {
        return MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
