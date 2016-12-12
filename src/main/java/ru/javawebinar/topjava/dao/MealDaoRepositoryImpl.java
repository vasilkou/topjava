package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.MealRepository;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealDaoRepositoryImpl implements GenericDao<Meal> {
    private static MealRepository mealRepository;

    public MealDaoRepositoryImpl() {
        mealRepository = new MealRepository();
    }

    @Override
    public void add(Meal meal) {
        mealRepository.add(meal);
    }

    @Override
    public void delete(long id) {
        mealRepository.delete(id);
    }

    @Override
    public void update(Meal meal) {
        mealRepository.update(meal);
    }

    @Override
    public List<Meal> getAll() {
        return mealRepository.getAll();
    }

    @Override
    public Meal getById(long id) {
        return mealRepository.getById(id);
    }
}
