package ru.practicum.children.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.children.dto.CategoryChildrenDto;
import ru.practicum.children.dto.NewChildrenDto;
import ru.practicum.children.dto.UpdateChildrenDto;
import ru.practicum.children.service.ChildrenService;

@RestController
@RequestMapping("/categories/children")
@RequiredArgsConstructor
public class ChildrenController {
    private final ChildrenService childrenService;

    @GetMapping("/{childrenId}")
    CategoryChildrenDto getById(@PathVariable @Positive Long childrenId) {
        return childrenService.getById(childrenId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryChildrenDto addChildren(@RequestBody @Valid NewChildrenDto dto) {
        return childrenService.createChildren(dto);
    }

    @DeleteMapping("/childrenId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteChildren(@PathVariable @Positive Long childrenId) {
        childrenService.deleteChildren(childrenId);
    }

    @PatchMapping("/childrenId")
    CategoryChildrenDto updateChildren(@RequestBody @Valid UpdateChildrenDto dto,
                                       @PathVariable @Positive Long childrenId) {
        return childrenService.updateChildren(dto, childrenId);
    }
}
