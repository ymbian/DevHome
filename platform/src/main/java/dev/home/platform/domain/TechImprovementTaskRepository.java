package dev.home.platform.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TechImprovementTaskRepository extends JpaRepository<TechImprovementTask, Long> {

    List<TechImprovementTask> findByTypeOrderByCreatedAtDesc(TechImprovementType type);

    Page<TechImprovementTask> findByTypeOrderByCreatedAtDesc(TechImprovementType type, Pageable pageable);

    List<TechImprovementTask> findByTypeInOrderByCreatedAtDesc(List<TechImprovementType> types);

    List<TechImprovementTask> findAllByOrderByCreatedAtDesc();

    Page<TechImprovementTask> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
