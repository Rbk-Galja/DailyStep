package ru.practicum.category.service;

import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.dto.NewParentRequest;
import ru.practicum.category.dto.UpdateParentDto;

import java.util.List;

public interface ParentService {
    CategoryParentDto getCategoryById(Long id);

    List<CategoryParentDto> getPopularCategories();

    CategoryParentDto addCategory(NewParentRequest category);

    void deleteCategory(Long id);

    CategoryParentDto updateCategory(UpdateParentDto dto, Long id);

    CategoryParentDto addChildrenCategory(List<Long> ids, Long id);

    CategoryParentDto deleteChildrenCategory(List<Long> ids, Long id);
}
