package ru.practicum.habit.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.habit.dto.HabitDto;
import ru.practicum.habit.dto.NewHabitRequest;
import ru.practicum.habit.dto.UpdateHabitRequest;
import ru.practicum.habit.service.HabitService;

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
}
