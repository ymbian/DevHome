package dev.home.platform.service;

import dev.home.platform.domain.PipelineInstance;
import dev.home.platform.domain.PipelineInstanceRepository;
import dev.home.platform.domain.TechImprovementTask;
import dev.home.platform.domain.TechImprovementTaskRepository;
import dev.home.platform.domain.TechImprovementType;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Dispatches tech improvement tasks to the development module: create pipeline, generate design, run openspec.
 */
@Service
public class TechDispatchService {

    private final PipelineInstanceRepository pipelineRepository;
    private final TechImprovementTaskRepository taskRepository;
    private final DesignGenerationService designService;
    private final OpenspecService openspecService;

    public TechDispatchService(PipelineInstanceRepository pipelineRepository,
                               TechImprovementTaskRepository taskRepository,
                               DesignGenerationService designService,
                               OpenspecService openspecService) {
        this.pipelineRepository = pipelineRepository;
        this.taskRepository = taskRepository;
        this.designService = designService;
        this.openspecService = openspecService;
    }

    /**
     * Dispatch one or more tech tasks: create pipeline per task, run design + openspec, link task to instance.
     */
    public List<DispatchResult> dispatch(List<Long> taskIds) {
        List<DispatchResult> results = new ArrayList<DispatchResult>();
        for (Long taskId : taskIds) {
            results.add(dispatchOne(taskId));
        }
        return results;
    }

    private DispatchResult dispatchOne(Long taskId) {
        DispatchResult r = new DispatchResult();
        r.setTaskId(taskId);
        TechImprovementTask task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            r.setSuccess(false);
            r.setError("Task not found");
            return r;
        }
        String requirementText = buildRequirementText(task);
        String instanceId;
        try {
            PipelineInstance p = new PipelineInstance();
            p.setInstanceId("req-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12));
            p.setRequirementText(requirementText);
            p.setCreatedAt(Instant.now());
            p.setStatus("SUBMITTED");
            p = pipelineRepository.save(p);
            instanceId = p.getInstanceId();
        } catch (Exception e) {
            r.setSuccess(false);
            r.setError("Create pipeline: " + e.getMessage());
            return r;
        }
        try {
            designService.generate(instanceId);
        } catch (Exception e) {
            r.setSuccess(false);
            r.setInstanceId(instanceId);
            r.setError("Design generate: " + e.getMessage());
            return r;
        }
        try {
            openspecService.runOpenspec(instanceId);
        } catch (Exception e) {
            r.setSuccess(false);
            r.setInstanceId(instanceId);
            r.setError("Openspec run: " + e.getMessage());
            return r;
        }
        task.setPipelineInstanceId(instanceId);
        task.setStatus("DISPATCHED");
        taskRepository.save(task);
        r.setSuccess(true);
        r.setInstanceId(instanceId);
        return r;
    }

    private static String buildRequirementText(TechImprovementTask task) {
        String typeLabel = task.getType() != null ? task.getType().getLabel() : "技改";
        StringBuilder sb = new StringBuilder();
        sb.append("[技改-").append(typeLabel).append("] ").append(task.getTitle());
        if (task.getDescription() != null && !task.getDescription().isEmpty()) {
            sb.append("\n\n").append(task.getDescription());
        }
        return sb.toString();
    }

    public static class DispatchResult {
        private Long taskId;
        private boolean success;
        private String instanceId;
        private String error;
        public Long getTaskId() { return taskId; }
        public void setTaskId(Long taskId) { this.taskId = taskId; }
        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getInstanceId() { return instanceId; }
        public void setInstanceId(String instanceId) { this.instanceId = instanceId; }
        public String getError() { return error; }
        public void setError(String error) { this.error = error; }
    }
}
