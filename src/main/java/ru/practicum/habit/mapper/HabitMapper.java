package ru.practicum.habit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.habit.dto.HabitDto;
import ru.practicum.habit.dto.HabitShortDto;
import ru.practicum.habit.dto.NewHabitRequest;
import ru.practicum.habit.model.Habit;
import ru.practicum.helper.RequestParamHelper;

@Mapper(componentModel = "spring")
public interface HabitMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", source = "parent")
    @Mapping(target = "start", dateFormat = RequestParamHelper.DATE_TIME_FORMAT)
    Habit mapToHabitNew(NewHabitRequest request, CategoryParent parent);

    @Mapping(target = "category", source = "categoryDto")
    @Mapping(target = "start", dateFormat = RequestParamHelper.DATE_TIME_FORMAT)
    HabitDto mapToHabitDto(Habit habit, CategoryParentDto categoryDto);

    @Mapping(target = "categoryName", source = "categoryName")
    HabitShortDto mapToShortDto(Habit habit, String categoryName);
}
