package ru.practicum.habit.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.habit.dto.HabitDto;
import ru.practicum.habit.dto.HabitShortDto;
import ru.practicum.habit.dto.NewHabitRequest;
import ru.practicum.habit.dto.UpdateHabitRequest;
import ru.practicum.habit.service.HabitService;
import ru.practicum.helper.RequestParamHelper;

import java.time.LocalDateTime;
import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/habits")
@RequiredArgsConstructor
public class HabitController {
    private final HabitService habitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    HabitDto addHabit(@RequestBody @Valid NewHabitRequest request) {
        return habitService.addHabit(request);
    }

    @PatchMapping("/{habitId}")
    HabitDto updateHabit(@PathVariable @Positive Long habitId,
                         @Valid @RequestBody UpdateHabitRequest request) {
        return habitService.updateHabit(request, habitId);
    }

    @DeleteMapping("/habitId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteHabit(@PathVariable @Positive Long habitId) {
        habitService.deleteHabit(habitId);
    }

    @GetMapping("/habitId")
    HabitDto getById(@PathVariable @Positive Long habitId) {
        return habitService.getById(habitId);
    }

    @GetMapping("/category/{categoryId}")
    List<HabitDto> getHabitByCategory(@PathVariable @Positive Long categoryId) {
        return habitService.getHabitByCategory(categoryId);
    }
}
