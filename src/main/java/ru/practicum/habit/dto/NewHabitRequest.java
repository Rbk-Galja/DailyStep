package ru.practicum.habit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.helper.RequestParamHelper;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewHabitRequest {
    Long categoryParent;

    @NotBlank
    @Size(min = 2, max = 120)
    String title;

    @Size(min = 20, max = 7000)
    String description;

    @NotBlank
    @JsonFormat(pattern = RequestParamHelper.DATE_TIME_FORMAT)
    LocalDateTime start;
}
