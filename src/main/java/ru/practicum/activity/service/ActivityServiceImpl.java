package ru.practicum.activity.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.activity.dto.ActivityTypeDto;
import ru.practicum.activity.dto.NewActivityRequest;
import ru.practicum.activity.dto.UpdateActivityRequest;
import ru.practicum.activity.mapper.ActivityMapper;
import ru.practicum.activity.model.ActivityType;
import ru.practicum.activity.repository.ActivityRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.streak.service.StreakService;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Transactional
    @Override
    public ActivityTypeDto create(NewActivityRequest request) {
        log.info("Начинаем создание ActivityType {}", request);
        ActivityType create = activityMapper.mapToActivityNew(request);
        ActivityType activityType = activityRepository.save(create);
        log.info("Создание ActivityType завершено {}", activityType);
        return activityMapper.mapToDto(activityType);
    }

    @Override
    public ActivityTypeDto getById(Long id) {
        log.info("Начинаем получение ActivityType по id = {}", id);
        return activityMapper.mapToDto(findById(id));
    }

    @Override
    public ActivityTypeDto update(UpdateActivityRequest request, Long id) {
        log.info("Начинаем обновление ActivityType id = {}", id);
        ActivityType activityType = findById(id);
        log.info("Определен ActivityType для обновления: {}", activityType);

        if (request.getName() != null) {
            activityType.setName(request.getName());
            log.info("Обновлено имя ActivityType id = {}", id);
        }
        if (request.getCode() != null) {
            activityType.setCode(request.getCode());
            log.info("Обновлен code ActivityType id = {}", id);
        }
        if (request.getActivityCategory() != null) {
            activityType.setActivityCategory(request.getActivityCategory());
            log.info("Обновлен ActivityCategory ActivityType id = {}", id);
        }
        if (request.getUnit() != null) {
            activityType.setUnit(request.getUnit());
            log.info("Обновлено значение unit ActivityType id = {}", id);
        }
        if (request.getIconName() != null) {
            activityType.setUnit(request.getUnit());
            log.info("Обновлено iconName ActivityType id = {}", id);
        }
        activityRepository.save(activityType);
        log.info("Обновление ActivityType завершено: {}", activityType);
        return activityMapper.mapToDto(activityType);
    }

    @Override
    public void delete(Long id) {
        log.info("Начинаем удаление ActivityType id = {}", id);
        ActivityType activityType = findById(id);
        activityRepository.delete(activityType);
        log.info("Удаление ActivityType id = {} завершено", id);
    }

    private ActivityType findById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ActivityType", id));
    }
}
