package ru.practicum.habit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.habit.model.Habit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByCategoryId(Long categoryId);

    List<Habit> findByStart(LocalDateTime start);

    List<Habit> findByStartBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = """
            SELECT h.title, h.start, h.category FROM Habit h
            WHERE h.start >= :cutoffDate
            GROUP BY h.category
            ORDER BY COUNT(h) DESC
            """)
    List<Habit> findHabitForWeek(@Param("cutoffDate") LocalDateTime cutoffDate);
}
