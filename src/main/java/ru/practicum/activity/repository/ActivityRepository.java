package ru.practicum.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.activity.model.ActivityType;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityType, Long> {
}
