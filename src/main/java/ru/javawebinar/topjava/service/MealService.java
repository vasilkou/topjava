package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface MealService {
    Meal save(Meal meal);

    // false if not found or wrong userId
    void delete(int id, int userId);

    // null if not found or wrong userId
    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getDateFiltered(int userId, LocalDate start, LocalDate end);
}
