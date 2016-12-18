package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class MealRestController {
    private static final Logger LOG = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Meal save(Meal meal) {
        LOG.info("save " + meal);
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll() {
        LOG.info("get all");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(String startDate, String endDate, String startTime, String endTime) {
        LOG.info("get Filtered between " + startDate + " " + startTime + " and " + endDate + " " + endTime);
        List<Meal> meals = service.getDateFiltered(
                AuthorizedUser.id(),
                "".equals(startDate) ? LocalDate.MIN : LocalDate.parse(startDate),
                "".equals(endDate) ? LocalDate.MAX : LocalDate.parse(endDate)
        );
        return MealsUtil.getFilteredWithExceeded(
                meals,
                "".equals(startTime) ? LocalTime.MIN : LocalTime.parse(startTime),
                "".equals(endTime) ? LocalTime.MAX : LocalTime.parse(endTime),
                AuthorizedUser.getCaloriesPerDay()
        );
    }
}
