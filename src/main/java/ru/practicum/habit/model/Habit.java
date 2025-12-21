package ru.practicum.habit.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.activity.model.ActivityType;
import ru.practicum.category.model.CategoryParent;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "habits")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    CategoryParent category;

    @Column(name = "title")
    String title;

    @Column(name = "description")
    String description;

    @Column(name = "start")
    LocalDateTime start;

    @ManyToOne
    @JoinColumn(name = "activity_type_id")
    ActivityType activityType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return Objects.equals(id, habit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
