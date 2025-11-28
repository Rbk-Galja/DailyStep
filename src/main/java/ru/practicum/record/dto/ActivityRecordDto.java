package ru.practicum.record.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.habit.dto.HabitShortDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivityRecordDto {
    HabitShortDto habit;
    LocalDateTime startTime;
    LocalDateTime endTime;
    Integer value;
    String notes;
}
