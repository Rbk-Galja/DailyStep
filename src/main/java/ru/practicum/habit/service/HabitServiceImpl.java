package ru.practicum.habit.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final ParentRepository parentRepository;
    private final HabitMapper habitMapper;
    private final CategoryParentMapper parentMapper;

    @Override
    public HabitDto addHabit(NewHabitRequest request) {
        log.info("Начинаем создание новой активности {}", request);
        CategoryParent categoryParent = parentRepository.findById(request.getCategoryParent())
                .orElseThrow(() -> new NotFoundException("CategoryParent", request.getCategoryParent()));
        log.info("Определена категория для активности: {}", categoryParent);
        Habit habit = habitMapper.mapToHabitNew(request, categoryParent);
        habitRepository.save(habit);
        log.info("Создание активности {} завершено", habit);
        return habitMapper.mapToHabitDto(habit, mapToDto(categoryParent));
    }

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
        log.info("Обновление активности id = {} завершено", id);
        return habitMapper.mapToHabitDto(habit, mapToDto(habit.getCategory()));
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
        return habitMapper.mapToHabitDto(habit, mapToDto(habit.getCategory()));
    }

    @Override
    public List<HabitDto> getHabitByCategory(Long id) {
        log.info("Начинаем получение всех активностей для категории id = {}", id);
        CategoryParent categoryParent = parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CategoryParent", id));
        List<Habit> habits = habitRepository.findByCategoryId(id);
        return habits.stream()
                .map(habit -> habitMapper.mapToHabitDto(habit, mapToDto(categoryParent)))
                .toList();
    }

    @Override
    public List<HabitDto> findByStart(LocalDateTime start) {
    log.info("Начинаем получение активности по дате проведения {}", start);
    List<Habit> habits = habitRepository.findByStart(start);
    log.info("Получение активностей по времени проведения завершено: {}", habits);
    return habits.stream()
            .map(habit -> habitMapper.mapToHabitDto(habit, mapToDto(habit.getCategory())))
            .toList();
    }

    @Override
    public List<HabitDto> findByStartEnd(LocalDateTime startDate, LocalDateTime endTime) {
        log.info("Начинаем получение активности по начальной и конечной дате {}, {}", startDate, endTime);
        List<Habit> habits = habitRepository.findByStartBetween(startDate, endTime);
        log.info("Получение активностей в указанный период завершено {}", habits);
        return habits.stream()
                .map(habit -> habitMapper.mapToHabitDto(habit, mapToDto(habit.getCategory())))
                .toList();
    }

    @Override
    public List<HabitShortDto> findHabitByWeek() {
        log.info("Начинаем получение самой длинной серии за последние 7 дней");
        List<Habit> habits = habitRepository.findHabitForWeek(LocalDateTime.now().minusDays(7));
        if (habits.isEmpty()) {
            log.info("Активностей за последние 7 дней не найдено");
            return new ArrayList<>();
        }
        log.info("Найдена самая большая серия активностей за последние 7 дней: {}", habits);
        return habits.stream()
                .map(habit -> habitMapper.mapToShortDto(habit, habit.getCategory().getName()))
                .toList();
    }

    private CategoryParentDto mapToDto(CategoryParent categoryParent) {
        return parentMapper.mapToDto(categoryParent);
    }

    private Habit findById(Long id) {
        return habitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habit", id));
    }
}
