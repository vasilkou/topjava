package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealController {
    private static final Logger LOG = LoggerFactory.getLogger(MealController.class);

    @Autowired
    private MealService service;

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model) {
        int userId = AuthorizedUser.id();
        LOG.info("getAll for User {}", userId);
        model.addAttribute(
                "meals",
                MealsUtil.getWithExceeded(service.getAll(userId), AuthorizedUser.getCaloriesPerDay())
        );
        return "meals";
    }

    @RequestMapping(value = "/meals/filter", method = RequestMethod.POST)
    public String filter(HttpServletRequest request) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        int userId = AuthorizedUser.id();
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, userId);
        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : DateTimeUtil.MIN_DATE,
                        endDate != null ? endDate : DateTimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay()
        );
        request.setAttribute("meals", meals);
        return "meals";
    }

    @RequestMapping(value = "/meals/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request) {
        int id = getId(request);
        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/create", method = RequestMethod.GET)
    public String create(Model model) {
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "meal";
    }

    @RequestMapping(value = "/meals/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request) {
        request.setAttribute("meal", service.get(getId(request), AuthorizedUser.id()));
        return "meal";
    }

    @RequestMapping(value = "/meals/save", method = RequestMethod.POST)
    public String save(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        int userId = AuthorizedUser.id();
        if (request.getParameter("id").isEmpty()) {
            checkNew(meal);
            LOG.info("create {} for User {}", meal, userId);
            service.save(meal, userId);
        } else {
            checkIdConsistent(meal, getId(request));
            LOG.info("update {} for User {}", meal, userId);
            service.update(meal, userId);
        }
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
