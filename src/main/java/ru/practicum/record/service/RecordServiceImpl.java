package ru.practicum.record.service;

import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.practicum.exception.InvalidParamException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.habit.dto.HabitShortDto;
import ru.practicum.habit.mapper.HabitMapper;
import ru.practicum.habit.model.Habit;
import ru.practicum.habit.repository.HabitRepository;
import ru.practicum.record.dto.ActivityRecordDto;
import ru.practicum.record.dto.ActivitySearchParam;
import ru.practicum.record.dto.NewRecordRequest;
import ru.practicum.record.dto.UpdateRecordRequest;
import ru.practicum.record.mapper.RecordMapper;
import ru.practicum.record.model.ActivityRecord;
import ru.practicum.record.repository.RecordRepository;
import ru.practicum.streak.service.StreakService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor_ = @Autowired)
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;
    private final RecordMapper recordMapper;
    private final HabitRepository habitRepository;
    private final HabitMapper habitMapper;
    private final StreakService streakService;

    @Override
    public ActivityRecordDto create(NewRecordRequest request) {
        log.info("Начинаем создание ActivityRecord {}", request);
        Habit habit = findHabitById(request.getHabitId());
        log.info("Определена активность для ActivityRecord id = {}", request.getHabitId());
        ActivityRecord create = recordMapper.mapToRecordNew(request, habit);
        ActivityRecord activityRecord = recordRepository.save(create);
        streakService.updateHabitStreak(activityRecord.getHabit().getId(), activityRecord.getEndTime().toLocalDate());
        log.info("Создание ActivityRecord завершено: {}", activityRecord);
        return recordMapper.mapToDto(activityRecord, mapHabitToDto(habit));
    }

    @Override
    public ActivityRecordDto update(UpdateRecordRequest request, Long id) {
        log.info("Начинаем обноление ActivityRecord id = {}", id);
        ActivityRecord activityRecord = findById(id);
        if (request.getHabitId() != null) {
            activityRecord.setHabit(findHabitById(request.getHabitId()));
            log.info("Обновлена активность для ActivityRecord id = {}", id);
        }
        if (request.getStartTime() != null) {
            activityRecord.setStartTime(request.getStartTime());
            log.info("Обновлено время начала для ActivityRecord id = {}", id);
        }
        if (request.getEndTime() != null) {
            activityRecord.setEndTime(request.getEndTime());
            log.info("Обновлено время окончания для ActivityRecord id = {}", id);
        }
        if (request.getValue() != null) {
            activityRecord.setValue(request.getValue());
            log.info("Обновлено value для ActivityRecord id = {}", id);
        }
        if (request.getNotes() != null) {
            activityRecord.setNotes(request.getNotes());
            log.info("Обновлен комментарий для ActivityRecord id = {}", id);
        }
        recordRepository.save(activityRecord);
        log.info("Обноление ActivityRecord id = {} завершено", id);
        return recordMapper.mapToDto(activityRecord, mapHabitToDto(activityRecord.getHabit()));
    }

    @Override
    public void delete(Long id) {
        log.info("Начинаем удаление ActivityRecord id = {}", id);
        ActivityRecord activityRecord = findById(id);
        recordRepository.delete(activityRecord);
        log.info("Удаление ActivityRecord id = {} завершено", id);
    }

    @Override
    public List<ActivityRecordDto> getWithFilter(ActivitySearchParam param, Pageable page) {
        log.info("Начинаем получение активностей с фильтрами");
        Specification<ActivityRecord> spec = createSpec(param);
        List<ActivityRecord> records = recordRepository.findAll(spec, page);
        log.info("Возвращаем список активностей с поиском по фильтрам {}", records);
        return records.stream()
                .map(activityRecord -> recordMapper.mapToDto(activityRecord, mapHabitToDto(activityRecord.getHabit())))
                .toList();
    }

    private Specification<ActivityRecord> createSpec(final ActivitySearchParam param) {
        LocalDateTime rangeStart = param.getStart();
        LocalDateTime rangeEnd = param.getEnd();
        Long habitId = param.getHabitId();
        String text = param.getText();

        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            log.error("Конец выборки находится во временной шкале раньше начала");
            throw new InvalidParamException("Время окончания выборки не может быть раньше времени начала");
        }

        return (root, query, cb) -> {
            log.info("Начинаем фильтрацию переданных параметров для ActivityRecord");
            List<Predicate> predicates = new ArrayList<>();

            if (habitId != null && habitId != 0) {
                log.info("Фильтрация по habitId = {}", habitId);
                predicates.add(cb.equal(root.get("habit").get("id"), habitId));
            }

            if (rangeStart != null || rangeEnd != null) {
                if (rangeStart != null && rangeEnd != null) {
                    log.info("Фильтрация по rangeStart {} и rangeEnd {}", rangeStart, rangeEnd);
                    predicates.add(cb.between(root.get("startTime"), rangeStart, rangeEnd));
                } else if (rangeStart != null) {
                    log.info("Фильтрация по дате после startTime {}", rangeStart);
                    predicates.add(cb.greaterThanOrEqualTo(root.get("startTime"), rangeStart));
                } else if (rangeEnd != null) {
                    log.info("Фильтрация по дате до endTime {}", rangeEnd);
                    predicates.add(cb.lessThanOrEqualTo(root.get("startTime"), rangeEnd));
                }
            }

            if (text != null && !text.trim().isEmpty()) {
                log.info("Поиск по ключевым словам {} в поле notes", text);
                String normalizedText = "%" + text.trim().toLowerCase() + "%";
                Predicate notesPredicate = cb.like(
                        cb.lower(root.get("notes")),
                        normalizedText
                );
                predicates.add(notesPredicate);
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Habit findHabitById(Long id) {
        return habitRepository.findById(id).orElseThrow(() -> new NotFoundException("Habit", id));
    }

    private HabitShortDto mapHabitToDto(Habit habit) {
        return habitMapper.mapToShortDto(habit);
    }

    private ActivityRecord findById(Long id) {
        log.info("Возвращаем ActivityRecord по id = {}", id);
        return recordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ActivityRecord", id));
    }
}
