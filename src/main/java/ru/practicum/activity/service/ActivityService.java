package ru.practicum.activity.service;

import ru.practicum.activity.dto.ActivityTypeDto;
import ru.practicum.activity.dto.NewActivityRequest;
import ru.practicum.activity.dto.UpdateActivityRequest;

import java.util.List;

public interface ActivityService {
    ActivityTypeDto create(NewActivityRequest request);

    ActivityTypeDto getById(Long id);

    ActivityTypeDto update(UpdateActivityRequest request, Long id);

    void delete(Long id);

}
