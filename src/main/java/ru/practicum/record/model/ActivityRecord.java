package ru.practicum.record.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.habit.model.Habit;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity_records")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id")
    Habit habit;

    @JoinColumn(name = "start_time")
    LocalDateTime startTime;

    @JoinColumn(name = "end_time")
    LocalDateTime endTime;

    @JoinColumn(name = "val")
    Integer value;

    @JoinColumn(name = "notes")
    String notes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityRecord that = (ActivityRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
