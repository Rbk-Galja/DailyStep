package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.CategoryParent;

import java.util.List;

@Repository
public interface ParentRepository extends JpaRepository<CategoryParent, Long> {


    @Query("SELECT c " +
            "FROM CategoryParent c " +
            "LEFT JOIN Habit h ON c.id = h.category.id " +
            "GROUP BY c.id, c.name " +
            "ORDER BY COUNT(h.id) DESC " +
            "LIMIT 20")
    List<CategoryParent> getPopularCategory();
}

