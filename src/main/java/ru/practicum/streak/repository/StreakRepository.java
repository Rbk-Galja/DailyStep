package ru.practicum.streak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.streak.model.HabitStreak;

import java.time.LocalDate;
import java.util.List;

public interface StreakRepository extends JpaRepository<HabitStreak, Long> {
    List<HabitStreak> findByLastActivityDateBefore(LocalDate date);
}
