package ru.practicum.habit.service;

import ru.practicum.habit.dto.HabitDto;
import ru.practicum.habit.dto.NewHabitRequest;
import ru.practicum.habit.dto.UpdateHabitRequest;

public interface HabitService {
    HabitDto addHabit(NewHabitRequest request);

    HabitDto updateHabit(UpdateHabitRequest request, Long id);

    void deleteHabit(Long id);
}
