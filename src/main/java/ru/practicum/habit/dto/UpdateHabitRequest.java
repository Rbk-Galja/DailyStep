package ru.practicum.habit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.activity.dto.NewActivityRequest;
import ru.practicum.helper.RequestParamHelper;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateHabitRequest {
    @Size(min = 2, max = 120)
    String title;

    @Size(min = 20, max = 7000)
    String description;

    @Positive
    Long categoryParent;

    @DateTimeFormat(pattern = RequestParamHelper.DATE_TIME_FORMAT)
    @JsonFormat(pattern = RequestParamHelper.DATE_TIME_FORMAT)
    LocalDateTime start;

    @Positive
    Long activityId;

    NewActivityRequest newActivityRequest;

}
