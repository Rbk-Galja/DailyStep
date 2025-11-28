package ru.practicum.streak.service;

import ru.practicum.streak.model.HabitStreak;

import java.time.LocalDate;
import java.util.List;

public interface StreakService {
    void updateHabitStreak(Long habitId, LocalDate activityDate);

    List<HabitStreak> findBrokenStreak();

    String getRecommendation();

    String getRecommendationByActivityCategory();
}
