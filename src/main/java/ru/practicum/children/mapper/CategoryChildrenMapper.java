package ru.practicum.children.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.children.dto.CategoryChildrenDto;
import ru.practicum.children.dto.NewChildrenDto;
import ru.practicum.children.model.CategoryChildren;

@Mapper(componentModel = "spring")
public interface CategoryChildrenMapper {
    CategoryChildrenDto mapToDto(CategoryChildren category);

    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "id", ignore = true)
    CategoryChildren mapToDtoNew(NewChildrenDto dto, CategoryParent parent);
}
