package ru.practicum.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.dto.NewCategoryParentDto;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.category.service.ChildrenService;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public final class CategoryParentMapper {

    private final ChildrenService childrenService;
    private final CategoryChildrenMapper categoryChildrenMapper;

    public CategoryParent mapToCategoryNew(NewCategoryParentDto categoryDto) {
        CategoryParent category = new CategoryParent();
        category.setName(categoryDto.getName());
        if (categoryDto.getChildren() != null && !categoryDto.getChildren().isEmpty()) {
            category.setChildren(childrenService.findAllById(categoryDto.getChildren()));
        } else {
            category.setChildren(new ArrayList<>());
        }
        return category;
    }

    public CategoryParentDto mapToDto(CategoryParent category) {
        CategoryParentDto categoryDto = new CategoryParentDto();
        categoryDto.setName(category.getName());
        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            categoryDto.setChildren(category.getChildren().stream()
                    .map(categoryChildrenMapper::mapToDto)
                    .toList());
        } else {
            categoryDto.setChildren(new ArrayList<>());
        }
        return categoryDto;
    }
}
