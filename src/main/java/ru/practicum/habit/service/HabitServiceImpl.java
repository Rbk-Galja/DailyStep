package ru.practicum.habit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.category.mapper.CategoryParentMapper;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.category.repository.ParentRepository;
import ru.practicum.category.service.ParentService;
import ru.practicum.exception.NotFoundException;
import ru.practicum.habit.dto.HabitDto;
import ru.practicum.habit.dto.NewHabitRequest;
import ru.practicum.habit.dto.UpdateHabitRequest;
import ru.practicum.habit.mapper.HabitMapper;
import ru.practicum.habit.model.Habit;
import ru.practicum.habit.repository.HabitRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final ParentService parentService;
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
        return habitMapper.mapToHabitDto(habit, parentMapper.mapToDto(categoryParent));
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
        return habitMapper.mapToHabitDto(habit, parentMapper.mapToDto(habit.getCategory()));
    }

    @Override
    public void deleteHabit(Long id) {
        log.info("Начинаем удаление активности id = {}", id);
        Habit habit = findById(id);
        habitRepository.delete(habit);
    }

    private Habit findById(Long id) {
        return habitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Habit", id));
    }
}
