package ru.practicum.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.service.ChildrenService;
import ru.practicum.category.service.ParentService;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoriesController {
    private final ParentService parentService;
    private final ChildrenService childrenService;

    @GetMapping("/popular")
    List<CategoryParentDto> getPopularCategories() {
        return parentService.getPopularCategories();
    }

    @GetMapping("/{parentId}")
    CategoryParentDto getCategoryByID
}
