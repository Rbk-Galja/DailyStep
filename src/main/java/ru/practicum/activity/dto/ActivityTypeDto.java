package ru.practicum.activity.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.activity.model.ActivityCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityTypeDto {
    String name;
    String unit;
    String iconName;
    ActivityCategory activityCategory;
}
