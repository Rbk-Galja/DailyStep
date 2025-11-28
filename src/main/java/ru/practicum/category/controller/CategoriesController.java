package ru.practicum.category.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.dto.NewParentRequest;
import ru.practicum.category.dto.UpdateParentDto;
import ru.practicum.children.service.ChildrenService;
import ru.practicum.category.service.ParentService;

import java.util.List;

@RestController
@RequestMapping("/categories/parent")
@RequiredArgsConstructor
public class CategoriesController {
    private final ParentService parentService;

    @GetMapping("/popular")
    List<CategoryParentDto> getPopularCategories() {
        return parentService.getPopularCategories();
    }

    @GetMapping("/{parentId}")
    CategoryParentDto getCategoryById(@PathVariable @Positive Long parentId) {
        return parentService.getCategoryById(parentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CategoryParentDto addCategory(@RequestBody @Valid NewParentRequest request) {
        return parentService.addCategory(request);
    }

    @DeleteMapping("/{parentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable @Positive Long parentId) {
        parentService.deleteCategory(parentId);
    }

    @PatchMapping("/{parentId}")
    CategoryParentDto updateCategory(@RequestBody @Valid UpdateParentDto dto, @PathVariable @Positive Long parentId) {
        return parentService.updateCategory(dto, parentId);
    }

    @PatchMapping("/{parentId}/childrens")
    CategoryParentDto addChildrenCategory(@RequestParam List<Long> ids,
                                          @PathVariable @Positive Long parentId) {
        return parentService.addChildrenCategory(ids, parentId);
    }

    @DeleteMapping("/{parentId}/childrens")
    CategoryParentDto deleteChildrenCategory(@RequestParam List<Long> ids,
                                             @PathVariable @Positive Long parentId) {
        return parentService.deleteChildrenCategory(ids, parentId);
    }
}
