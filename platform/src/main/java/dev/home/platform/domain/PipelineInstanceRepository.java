package dev.home.platform.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PipelineInstanceRepository extends JpaRepository<PipelineInstance, Long> {

    Optional<PipelineInstance> findByInstanceId(String instanceId);

    List<PipelineInstance> findAllByOrderByCreatedAtDesc();
}
