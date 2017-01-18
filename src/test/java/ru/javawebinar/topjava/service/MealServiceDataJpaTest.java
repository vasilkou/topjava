package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class MealServiceDataJpaTest extends MealServiceTest {
    @Test
    public void testGetWithOwner() throws Exception {
        Meal meal = service.getWithOwner(ADMIN_MEAL_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, meal);
        UserTestData.MATCHER.assertEquals(UserTestData.ADMIN, meal.getUser());
    }
}
