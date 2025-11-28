package ru.practicum.children.service;

import ru.practicum.children.dto.CategoryChildrenDto;
import ru.practicum.children.dto.NewChildrenDto;
import ru.practicum.children.dto.UpdateChildrenDto;

public interface ChildrenService {
    CategoryChildrenDto getById(Long id);

    CategoryChildrenDto createChildren(NewChildrenDto dto);

    void deleteChildren(Long id);

    CategoryChildrenDto updateChildren(UpdateChildrenDto dto, Long id);
}
