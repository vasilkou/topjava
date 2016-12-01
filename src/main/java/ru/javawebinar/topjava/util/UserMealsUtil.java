package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // Counts eaten calories of every meal per day
        Map<LocalDate, Integer> eatenCaloriesPerDay = new HashMap<>();
        for (UserMeal meal : mealList) {
            eatenCaloriesPerDay.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum);
        }

        // Filters meals by LocalTime and adds them to mealWithExceedList
        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();
        for (UserMeal meal : mealList) {
            LocalDateTime dateAndTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateAndTime.toLocalTime(), startTime, endTime)) {
                int eatenCalories = eatenCaloriesPerDay.get(dateAndTime.toLocalDate());
                mealWithExceedList.add(new UserMealWithExceed(dateAndTime, meal.getDescription(), meal.getCalories(), eatenCalories > caloriesPerDay));
            }
        }

        return mealWithExceedList;
    }
}
