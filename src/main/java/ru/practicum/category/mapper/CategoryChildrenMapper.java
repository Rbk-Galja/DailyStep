package ru.practicum.category.mapper;

import org.mapstruct.Mapper;
import ru.practicum.category.dto.CategoryChildrenDto;
import ru.practicum.category.model.CategoryChildren;

@Mapper(componentModel = "spring")
public interface CategoryChildrenMapper {
    CategoryChildrenDto mapToDto(CategoryChildren category);
}
