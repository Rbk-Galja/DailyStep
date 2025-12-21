package ru.practicum.activity.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.activity.dto.ActivityTypeDto;
import ru.practicum.activity.dto.NewActivityRequest;
import ru.practicum.activity.dto.UpdateActivityRequest;
import ru.practicum.activity.service.ActivityService;

@RestController
@RequestMapping("/activity/type")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityTypeDto createActivity(@RequestBody @Valid NewActivityRequest request) {
        return activityService.create(request);
    }

    @GetMapping("/{activityId}")
    public ActivityTypeDto getById(@PathVariable @Positive Long activityId) {
        return activityService.getById(activityId);
    }

    @PatchMapping("/{activityId}")
    public ActivityTypeDto update(@PathVariable @Positive Long activityId,
                                  @RequestBody @Valid UpdateActivityRequest request) {
        return activityService.update(request, activityId);
    }

    @DeleteMapping("/{activityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @Positive Long activityId) {
        activityService.delete(activityId);
    }
}
