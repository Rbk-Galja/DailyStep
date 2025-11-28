package ru.practicum.record.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.record.model.ActivityRecord;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<ActivityRecord, Long> {
    List<ActivityRecord> findAll(Specification<ActivityRecord> spec, Pageable page);
}
