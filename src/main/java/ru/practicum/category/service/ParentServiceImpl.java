package ru.practicum.category.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.dto.NewParentRequest;
import ru.practicum.category.dto.UpdateParentDto;
import ru.practicum.category.mapper.CategoryParentMapper;
import ru.practicum.children.model.CategoryChildren;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.children.repository.ChildrenRepository;
import ru.practicum.category.repository.ParentRepository;
import ru.practicum.exception.EmptyParamException;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ParentServiceImpl implements ParentService {
    private final ParentRepository parentRepository;
    private final CategoryParentMapper categoryParentMapper;
    private final ChildrenRepository childrenRepository;

    @Override
    public CategoryParentDto getCategoryById(Long id) {
        log.info("Начинаем получение категории по id = {}", id);
        CategoryParent category = findById(id);
        return categoryParentMapper.mapToDto(category);
    }

    @Override
    public List<CategoryParentDto> getPopularCategories() {
        log.info("Начинаем получение 20 самых популярных категорий");
        return parentRepository.getPopularCategory().stream()
                .map(categoryParentMapper::mapToDto)
                .toList();
    }

    @Override
    public CategoryParentDto addCategory(NewParentRequest category) {
        log.info("Начинаем добавление категории {}", category);
        CategoryParent categoryParent = new CategoryParent();
        categoryParent.setName(category.getName());
        CategoryParent create = parentRepository.save(categoryParent);
        log.info("Категория успешно добавлена {}", create);
        return categoryParentMapper.mapToDto(create);
    }

    @Override
    public void deleteCategory(Long id) {
        log.info("Начинаем удаление категории id = {}", id);
        CategoryParent categoryParent = findById(id);
        parentRepository.delete(categoryParent);
        log.info("Категория id = {} успешно удалена", id);
    }

    @Override
    public CategoryParentDto updateCategory(UpdateParentDto dto, Long id) {
        log.info("Начинаем обновление категории id = {}", id);
        CategoryParent categoryParent = findById(id);
        categoryParent.setName(dto.getName());
        parentRepository.save(categoryParent);
        log.info("Обновление категории {} завершено", categoryParent);
        return categoryParentMapper.mapToDto(categoryParent);
    }

    @Transactional
    @Override
    public CategoryParentDto addChildrenCategory(List<Long> ids, Long id) {
        CategoryParent categoryParent = findById(id);

        if (ids == null || ids.isEmpty()) {
            throw new EmptyParamException("Передано пустое значение List");
        }

        List<CategoryChildren> foundChildren = childrenRepository.findByIdIn(ids);

        if (foundChildren.size() != ids.size()) {
            Set<Long> checkId = foundChildren.stream().map(CategoryChildren::getId)
                    .collect(Collectors.toSet());
            List<Long> notFound = ids.stream()
                    .filter(idCat -> !checkId.contains(idCat))
                    .toList();
            log.warn("Некоторые подкатегории из списка не найдены: {}", notFound);
        }

        List<Long> existingIds = categoryParent.getChildren().stream()
                .map(CategoryChildren::getId)
                .toList();
        Set<CategoryChildren> children = categoryParent.getChildren();

        foundChildren.stream()
                .filter(child -> !existingIds.contains(child.getId()))
                .forEach(children::add);
        parentRepository.save(categoryParent);
        return categoryParentMapper.mapToDto(categoryParent);
    }

    @Transactional
    @Override
    public CategoryParentDto deleteChildrenCategory(List<Long> ids, Long id) {
        if (ids == null || ids.isEmpty()) {
            throw new EmptyParamException("Передано пустое значение List");
        }
        childrenRepository.deleteByParentIdAndChildrenIds(id, ids);
        CategoryParent categoryParent = findById(id);
        return categoryParentMapper.mapToDto(categoryParent);
    }

    private CategoryParent findById(Long id) {
        return parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CategoryParent", id));
    }
}
