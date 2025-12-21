package ru.practicum.activity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.activity.model.ActivityCategory;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewActivityRequest {

    @Size(min = 2, max = 120)
    String name;

    @NotBlank
    @Size(min = 3, max = 30)
    String code;

    @Size(min = 1, max = 30)
    String unit;

    String iconName;

    @NotBlank
    ActivityCategory activityCategory;
}
