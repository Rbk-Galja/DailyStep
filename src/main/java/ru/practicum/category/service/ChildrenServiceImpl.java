package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.CategoryChildren;
import ru.practicum.category.repository.ChildrenRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChildrenServiceImpl implements ChildrenService {
    private final ChildrenRepository childrenRepository;

    @Override
    public List<CategoryChildren> findAllById(List<Long> ids) {
        log.info("Возвращаем список всех подкатегорий с id: {}", ids);
        return childrenRepository.findAllById(ids);
    }
}
