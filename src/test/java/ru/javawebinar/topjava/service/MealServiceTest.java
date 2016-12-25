package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    // Authorized user => ADMIN

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(ADMIN_MEAL_ID1, ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testGetWrongUser() throws Exception {
        service.get(ADMIN_MEAL_ID1, USER_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(ADMIN_MEAL_ID1, ADMIN_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(ADMIN_MEAL2), service.getAll(ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() {
        service.delete(1, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteWrongUser() {
        service.delete(ADMIN_MEAL_ID1, USER_ID);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(ADMIN_MEAL1);
        updated.setDescription("UpdatedMeal");
        updated.setCalories(666);
        service.update(updated, ADMIN_ID);
        MATCHER.assertEquals(updated, service.get(ADMIN_MEAL_ID1, ADMIN_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateWrongUser() throws Exception {
        service.update(new Meal(ADMIN_MEAL1), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateAlienMeal() throws Exception {
        service.update(new Meal(USER_MEAL), ADMIN_ID);
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "New meal", 1325);
        Meal created = service.save(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, ADMIN_MEAL2, ADMIN_MEAL1), service.getAll(ADMIN_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), all);
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        Collection<Meal> meals = service.getBetweenDates(START_DATE_TIME.toLocalDate(), END_DATE_TIME.toLocalDate(), ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), meals);
    }

    @Test
    public void testGetBetweenDateTimes() throws Exception {
        Collection<Meal> meals = service.getBetweenDateTimes(START_DATE_TIME, END_DATE_TIME, ADMIN_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(ADMIN_MEAL2, ADMIN_MEAL1), meals);
    }
}
