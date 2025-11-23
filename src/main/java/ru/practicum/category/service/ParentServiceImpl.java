package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.mapper.CategoryParentMapper;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.category.repository.ParentRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParentServiceImpl implements ParentService {
    private final ParentRepository parentRepository;
    private final CategoryParentMapper categoryParentMapper;

    @Override
    public CategoryParentDto getCategoryById(Long id) {
        log.info("Начинаем получение категории по id = {}", id);
        CategoryParent category = parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CategoryParent", id));
        return categoryParentMapper.mapToDto(category);
    }

    @Override
    public List<CategoryParentDto> getPopularCategories() {
        log.info("Начинаем получение 20 самых популярных категорий");
        return parentRepository.getPopularCategory().stream()
                .map(categoryParentMapper::mapToDto)
                .toList();
    }
}
