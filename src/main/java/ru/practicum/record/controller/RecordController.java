package ru.practicum.record.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.record.dto.ActivityRecordDto;
import ru.practicum.record.dto.ActivitySearchParam;
import ru.practicum.record.dto.NewRecordRequest;
import ru.practicum.record.dto.UpdateRecordRequest;
import ru.practicum.record.service.RecordService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/activity/record")
@RequiredArgsConstructor
public class RecordController {
    private final RecordService recordService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActivityRecordDto create(@RequestBody @Valid NewRecordRequest request) {
        return recordService.create(request);
    }

    @PatchMapping("/recordId")
    public ActivityRecordDto update(@RequestBody @Valid UpdateRecordRequest request,
                                    @PathVariable @Positive Long recordId) {
        return recordService.update(request, recordId);
    }

    @DeleteMapping("/recordId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable @Positive Long recordId) {
        recordService.delete(recordId);
    }

    @GetMapping
    public List<ActivityRecordDto> getWithFilter(@RequestParam @Positive Long habitId,
                                                 @RequestParam(required = false) LocalDateTime start,
                                                 @RequestParam(required = false) LocalDateTime end,
                                                 @RequestParam(required = false) String text,
                                                 @RequestParam(defaultValue = "0", required = false)
                                                 @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "10", required = false)
                                                 @PositiveOrZero Integer size) {
        Pageable page = PageRequest.of(from, size);
        ActivitySearchParam param = ActivitySearchParam.builder()
                .habitId(habitId)
                .start(start)
                .end(end)
                .text(text)
                .build();
        return recordService.getWithFilter(param, page);
    }
}
