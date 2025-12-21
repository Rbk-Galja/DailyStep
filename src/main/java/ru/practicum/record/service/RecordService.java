package ru.practicum.record.service;

import org.springframework.data.domain.Pageable;
import ru.practicum.record.dto.ActivityRecordDto;
import ru.practicum.record.dto.ActivitySearchParam;
import ru.practicum.record.dto.NewRecordRequest;
import ru.practicum.record.dto.UpdateRecordRequest;

import java.util.List;

public interface RecordService {
    ActivityRecordDto create(NewRecordRequest request);

    ActivityRecordDto update(UpdateRecordRequest request, Long id);

    void delete(Long id);

    List<ActivityRecordDto> getWithFilter(ActivitySearchParam param, Pageable page);
}
