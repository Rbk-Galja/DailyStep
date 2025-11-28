package ru.practicum.category.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.category.service.ParentService;
import ru.practicum.children.service.ChildrenService;
import ru.practicum.children.mapper.CategoryChildrenMapper;

import java.util.ArrayList;

@RequiredArgsConstructor
@Component
public final class CategoryParentMapper {
    private final CategoryChildrenMapper categoryChildrenMapper;

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
