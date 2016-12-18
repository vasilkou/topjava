package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal meal);

    // false if not found or wrong userId
    boolean delete(int id, int userId);

    // null if not found or wrong userId
    Meal get(int id, int userId);

    List<Meal> getAll(int userId);

    List<Meal> getDateFiltered(int userId, LocalDate start, LocalDate end);
}
