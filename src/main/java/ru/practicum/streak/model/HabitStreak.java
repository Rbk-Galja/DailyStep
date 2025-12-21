package ru.practicum.streak.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.habit.model.Habit;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "habit_streaks")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HabitStreak {
    @Id
    @Column(name = "habit_id")
    Long habitId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "habit_id")
    Habit habit;

    @Column(name = "current_streak", nullable = false)
    Integer currentStreak = 0;

    @Column(name = "last_activity_date")
    LocalDate lastActivityDate;

    @Column(name = "longest_streak", nullable = false)
    Integer longestStreak;

    @Column(name = "updated_at", nullable = false)
    OffsetDateTime updatedAt;

    public HabitStreak(Long habitId) {
        this.habitId = habitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HabitStreak that = (HabitStreak) o;
        return Objects.equals(habitId, that.habitId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(habitId);
    }
}
