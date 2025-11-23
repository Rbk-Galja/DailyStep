package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.CategoryParent;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<CategoryParent, Long> {

    @Modifying
    @Query("SELECT c COUNT(h.id) AS habit_count" +
            "FROM categories c" +
            "LEFT JOIN" +
            "habits h ON c.id = h.category_id" +
            "GROUP BY" +
            "c.id, c.name" +
            "ORDER BY" +
            "habit_count DESC" +
            "LIMIT 20")
    List<CategoryParent> getPopularCategory();
}
