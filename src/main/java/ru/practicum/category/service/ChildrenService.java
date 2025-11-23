package ru.practicum.category.service;

import ru.practicum.category.model.CategoryChildren;

import java.util.List;

public interface ChildrenService {
    List<CategoryChildren> findAllById(List<Long> ids);
}
