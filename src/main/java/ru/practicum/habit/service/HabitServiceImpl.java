package ru.practicum.habit.service;

import jakarta.transaction.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.activity.mapper.ActivityMapper;
import ru.practicum.activity.model.ActivityType;
import ru.practicum.activity.repository.ActivityRepository;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.category.repository.ParentRepository;
import ru.practicum.exception.EmptyParamException;
import ru.practicum.exception.InvalidParamException;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.mapper.CategoryParentMapper;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.category.repository.ParentRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.habit.dto.HabitDto;
import ru.practicum.habit.dto.HabitShortDto;
import ru.practicum.habit.dto.NewHabitRequest;
import ru.practicum.habit.dto.UpdateHabitRequest;
import ru.practicum.habit.mapper.HabitMapper;
import ru.practicum.habit.model.Habit;
import ru.practicum.habit.repository.HabitRepository;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class HabitServiceImpl implements HabitService {
   private final HabitRepository habitRepository;
    private final ParentRepository parentRepository;
    private final HabitMapper habitMapper;
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;

    @Transactional
    @Override
    public HabitDto addHabit(NewHabitRequest request) {
        log.info("Начинаем создание новой активности {}", request);

        CategoryParent categoryParent = parentRepository.findById(request.getCategoryParent())
                .orElseThrow(() -> new NotFoundException("CategoryParent", request.getCategoryParent()));
        log.info("Определена категория для активности: {}", categoryParent);

        ActivityType activityType = checkActivityType(request);
        Habit create = habitMapper.mapToHabitNew(request, categoryParent, activityType);
        Habit habit = habitRepository.save(create);
        log.info("Создание активности {} завершено", habit);
        return habitMapper.mapToHabitDto(habit);
    }

    @Transactional
    @Override
    public HabitDto updateHabit(UpdateHabitRequest request, Long id) {
        log.info("Начинаем обновление активности с id = {}", id);
        Habit habit = findById(id);
        if (request.getTitle() != null) {
            habit.setTitle(request.getTitle());
            log.info("Обновлено имя активности id = {}", id);
        }
        if (request.getCategoryParent() != null) {
            CategoryParent category = parentRepository.findById(request.getCategoryParent())
                    .orElseThrow(() -> new NotFoundException("CategoryParent", request.getCategoryParent()));
            habit.setCategory(category);
            log.info("Обновлена категория активности id = {}", id);
        }
        if (request.getDescription() != null) {
            habit.setDescription(request.getDescription());
            log.info("Обновлено описание активности id = {}", id);
        }
        if (request.getStart() != null) {
            habit.setStart(request.getStart());
            log.info("Обновлено время активности id = {}", id);
        }
        if (request.getNewActivityRequest() != null && request.getActivityId() != null) {
            log.warn("Указан существующий и новый ActivityType");
            throw new InvalidParamException("Нельзя указать одновременно существующий и новый ActivityType");
        }
        if (request.getNewActivityRequest() != null) {
            habit.setActivityType(activityRepository
                    .save(activityMapper.mapToActivityNew(request.getNewActivityRequest())));
            log.info("Добавлен новый ActivityType для id = {}", id);
        }
        if (request.getActivityId() != null) {
            habit.setActivityType(getActivityById(request.getActivityId()));
            log.info("Добавлен существующий ActivityType для id = {}", id);
        }
        habitRepository.save(habit);
        log.info("Обновление активности id = {} завершено", id);
        return habitMapper.mapToHabitDto(habit);
    }

    @Override
    public void deleteHabit(Long id) {
        log.info("Начинаем удаление активности id = {}", id);
        Habit habit = findById(id);
        habitRepository.delete(habit);
    }

    @Override
    public HabitDto getById(Long id) {
        log.info("Начинаем получение активности по id = {}", id);
        Habit habit = findById(id);
        return habitMapper.mapToHabitDto(habit);
    }

    @Transactional
    @Override
    public List<HabitDto> getHabitByCategory(Long id) {
        log.info("Начинаем получение всех активностей для категории id = {}", id);
        parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CategoryParent", id));
        List<Habit> habits = habitRepository.findByCategoryId(id);
        return habits.stream()
                .map(habitMapper::mapToHabitDto)
                .toList();
    }

    private ActivityType checkActivityType(NewHabitRequest request) {
        if (request.getNewActivityRequest() != null && request.getActivityId() != null) {
            log.warn("Указан и существующий и новый ActivityType для {}", request);
            throw new InvalidParamException("Нельзя указать одновременно существующий и новый ActivityType");
        }
        if (request.getActivityId() != null) {
            log.info("Возвращаем сущствующий ActivityType для {}", request);
            return getActivityById(request.getActivityId());
        }
        if (request.getNewActivityRequest() != null) {
            ActivityType activityType = activityMapper.mapToActivityNew(request.getNewActivityRequest());
            log.info("Создан новый ActivityType для {}", request);
            return activityRepository.save(activityType);
        }
        throw new EmptyParamException("Указаны пустые параметры activityId и NewActivityRequest");
    }

    private Habit findById(Long id) {
        log.info("Возвращаем Habit по id = {}", id);
        return habitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habit", id));
    }

    private ActivityType getActivityById(Long id) {
        log.info("Возвращаем ActivityType по id = {}", id);
        return activityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ActivityType", id));
    }
}
