package ru.practicum.record.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateRecordRequest {
    @Positive
    Long habitId;

    LocalDateTime startTime;

    LocalDateTime endTime;

    Integer value;

    @Size(min = 2, max = 1000)
    String notes;
}
