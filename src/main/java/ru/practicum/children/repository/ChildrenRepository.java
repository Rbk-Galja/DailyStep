package ru.practicum.children.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.children.model.CategoryChildren;

import java.util.List;

@Repository
public interface ChildrenRepository extends JpaRepository<CategoryChildren, Long> {

    @Query("SELECT c FROM CategoryChildren c WHERE c.id IN (:ids)")
    List<CategoryChildren> findByIdIn(@Param("ids") List<Long> ids);

    @Modifying
    @Transactional
    @Query("DELETE FROM CategoryChildren cc WHERE cc.parent.id = :parentId AND cc.id IN :childIds")
    void deleteByParentIdAndChildrenIds(@Param("parentId") Long parentId, @Param("childIds") List<Long> childIds);

}
