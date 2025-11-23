package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryParentDto;

import java.util.List;

public interface ParentService {
    CategoryParentDto getCategoryById(Long id);

    List<CategoryParentDto> getPopularCategories();
}
