package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealRepository {
    private static final AtomicLong id = new AtomicLong();

    private static final ConcurrentHashMap<Long, Meal> meals = new ConcurrentHashMap<>();

    static {
        populateMeals();
    }

    public void add(Meal meal) {
        meal.setId(id.incrementAndGet());
        meals.put(meal.getId(), meal);
    }

    public void delete(long id) {
        meals.remove(id);
    }

    public void update(Meal meal) {
        meals.replace(meal.getId(), meal);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    public Meal getById(long id) {
        return meals.get(id);
    }

    private static void populateMeals() {
        List<Meal> mealList = MealsUtil.createMealList();
        mealList.forEach(meal -> {
            if (meal.getId() == null) {
                meal.setId(id.incrementAndGet());
            }
            meals.put(meal.getId(), meal);
        });
    }
}
