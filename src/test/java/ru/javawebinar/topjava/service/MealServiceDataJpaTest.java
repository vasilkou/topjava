package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.MATCHER;

@ActiveProfiles(Profiles.DATAJPA)
public class MealServiceDataJpaTest extends MealServiceTest {
    public MealServiceDataJpaTest() {
        log = LoggerFactory.getLogger(MealServiceDataJpaTest.class);
    }

    @Test
    public void testGetWithOwner() throws Exception {
        Meal meal = service.getWithOwner(ADMIN_MEAL_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, meal);
        UserTestData.MATCHER.assertEquals(UserTestData.ADMIN, meal.getUser());
    }
}
