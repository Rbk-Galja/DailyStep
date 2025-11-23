package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.CategoryChildren;

import java.util.List;

@Repository
public interface ChildrenRepository extends JpaRepository<CategoryChildren, Long> {

    @Query("SELECT c FROM Children c WHERE c.id IN (:ids)")
    List<CategoryChildren> findByIdIn(@Param("ids") List<Long> ids);
}
