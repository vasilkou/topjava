package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class UserServiceDataJpaTest extends UserServiceTest {
    @Test
    public void testGetWithMeals() {
        User user = service.getWithMeals(USER_ID);
        MATCHER.assertEquals(USER, user);
        List<Meal> meals = user.getMeals();
        meals.sort((o1, o2) -> o2.getDateTime().compareTo(o1.getDateTime()));
        MealTestData.MATCHER.assertCollectionEquals(MealTestData.MEALS, meals);
    }
}
