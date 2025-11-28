package ru.practicum.habit.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.activity.mapper.ActivityMapper;
import ru.practicum.activity.model.ActivityType;
import ru.practicum.category.mapper.CategoryParentMapper;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.habit.dto.HabitDto;
import ru.practicum.habit.dto.HabitShortDto;
import ru.practicum.habit.dto.NewHabitRequest;
import ru.practicum.habit.model.Habit;

@Component
@RequiredArgsConstructor
public final class HabitMapper {
    private final CategoryParentMapper categoryParentMapper;
    private final ActivityMapper activityMapper;

    public Habit mapToHabitNew(NewHabitRequest request, CategoryParent parent, ActivityType activityType) {
        return Habit.builder()
                .title(request.getTitle())
                .start(request.getStart())
                .description(request.getDescription())
                .activityType(activityType)
                .category(parent)
                .build();
    }

    public HabitDto mapToHabitDto(Habit habit) {
        return HabitDto.builder()
                .title(habit.getTitle())
                .description(habit.getDescription())
                .start(habit.getStart())
                .activity(activityMapper.mapToDto(habit.getActivityType()))
                .category(categoryParentMapper.mapToDto(habit.getCategory()))
                .build();
    }

    public HabitShortDto mapToShortDto(Habit habit) {
        return HabitShortDto.builder()
                .title(habit.getTitle())
                .start(habit.getStart())
                .categoryName(habit.getCategory().getName())
                .build();
    }

}
