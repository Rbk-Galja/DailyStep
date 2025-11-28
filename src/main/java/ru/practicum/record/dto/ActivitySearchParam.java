package ru.practicum.record.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ActivitySearchParam {
    Long habitId;
    LocalDateTime start;
    LocalDateTime end;
    String text;
}
