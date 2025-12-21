package ru.practicum.habit.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.activity.dto.ActivityTypeDto;
import ru.practicum.category.dto.CategoryParentDto;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HabitDto {
    String title;
    String description;
    CategoryParentDto category;
    LocalDateTime start;
    ActivityTypeDto activity;
}
