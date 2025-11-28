package ru.practicum.record.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.practicum.habit.dto.HabitShortDto;
import ru.practicum.habit.model.Habit;
import ru.practicum.record.dto.ActivityRecordDto;
import ru.practicum.record.dto.NewRecordRequest;
import ru.practicum.record.model.ActivityRecord;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RecordMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "habit", source = "habit")
    ActivityRecord mapToRecordNew(NewRecordRequest request, Habit habit);

    @Mapping(target = "habit", source = "habit")
    ActivityRecordDto mapToDto(ActivityRecord activityRecord, HabitShortDto habit);
}

