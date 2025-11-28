package ru.practicum.children.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryParentDto;
import ru.practicum.category.mapper.CategoryParentMapper;
import ru.practicum.category.model.CategoryParent;
import ru.practicum.category.repository.ParentRepository;
import ru.practicum.children.dto.CategoryChildrenDto;
import ru.practicum.children.dto.NewChildrenDto;
import ru.practicum.children.dto.UpdateChildrenDto;
import ru.practicum.children.mapper.CategoryChildrenMapper;
import ru.practicum.children.model.CategoryChildren;
import ru.practicum.children.repository.ChildrenRepository;
import ru.practicum.exception.NotFoundException;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ChildrenServiceImpl implements ChildrenService {
    private final ChildrenRepository childrenRepository;
    private final CategoryChildrenMapper categoryChildrenMapper;
    private final ParentRepository parentRepository;

    @Override
    public CategoryChildrenDto getById(Long id) {
        log.info("Начинаем получение подкатегории по id = {}", id);
        CategoryChildren children = findById(id);
        log.info("Получение подкатегории завершено {}", children);
        return categoryChildrenMapper.mapToDto(children);
    }

    @Override
    public CategoryChildrenDto createChildren(NewChildrenDto dto) {
        log.info("Начинаем создание подкатегории {}", dto);
        CategoryParent categoryParent = findParentById(dto.getParent());
        log.info("Определена категория для подкатегории: {}", categoryParent);
        CategoryChildren children = categoryChildrenMapper.mapToDtoNew(dto, categoryParent);
        childrenRepository.save(children);
        log.info("Создание подкатегории завершено {}", children);
        return categoryChildrenMapper.mapToDto(children);
    }

    @Override
    public void deleteChildren(Long id) {
        log.info("Начинаем удаление подкатегории id = {}", id);
        CategoryChildren children = findById(id);
        childrenRepository.delete(children);
        log.info("Удаление категории {} завершено", children);
    }

    @Override
    public CategoryChildrenDto updateChildren(UpdateChildrenDto dto, Long id) {
        log.info("Начинаем обновление подкатегории id = {}", id);
        CategoryChildren children = findById(id);
        children.setNamed(dto.getNamed());
        childrenRepository.save(children);
        log.info("Обновление подкатегории завершено {}", children);
        return categoryChildrenMapper.mapToDto(children);
    }

    private CategoryChildren findById(Long id) {
        log.info("Получаем подкатегорию по Id = {}", id);
        return childrenRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CategoryChildren", id));
    }

    private CategoryParent findParentById(Long id) {
        log.info("Получаем категорию по Id = {}", id);
        return parentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CategoryParent", id));
    }
}
