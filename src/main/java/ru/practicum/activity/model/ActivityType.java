package ru.practicum.activity.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "activity_types")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "code", nullable = false, unique = true)
    String code;

    @Column(name = "name")
    String name;

    @Column(name = "unit")
    String unit;

    @Column(name = "icon_name")
    String iconName;

    @Enumerated(EnumType.STRING)
    @Column(name = "activity_category", nullable = false)
    ActivityCategory activityCategory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityType that = (ActivityType) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
