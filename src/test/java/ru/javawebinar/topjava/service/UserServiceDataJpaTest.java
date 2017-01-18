package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class UserServiceDataJpaTest extends UserServiceTest {

    public UserServiceDataJpaTest() {
        log = LoggerFactory.getLogger(UserServiceDataJpaTest.class);
    }

    @Test
    public void testGetWithMeals() {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        List<Meal> meals = user.getMeals();
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.MEALS, meals);
    }
}
