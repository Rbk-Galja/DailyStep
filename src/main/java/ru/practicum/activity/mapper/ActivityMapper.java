package ru.practicum.activity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.activity.dto.ActivityTypeDto;
import ru.practicum.activity.dto.NewActivityRequest;
import ru.practicum.activity.model.ActivityType;

@Mapper(componentModel = "spring")
public interface ActivityMapper {
    ActivityTypeDto mapToDto(ActivityType activityType);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    ActivityType mapToActivityNew(NewActivityRequest request);
}
