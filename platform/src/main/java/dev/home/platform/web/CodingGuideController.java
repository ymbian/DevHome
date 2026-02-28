package dev.home.platform.web;

import dev.home.platform.domain.PipelineInstanceRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Exposes "coding ready" status and task/context summary for Cursor (e.g. /opsx:apply).
 */
@RestController
@RequestMapping("/api/instances/{id}")
public class CodingGuideController {

    private final PipelineInstanceRepository repository;

    public CodingGuideController(PipelineInstanceRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/coding-guide")
    public ResponseEntity<Map<String, Object>> codingGuide(@PathVariable String id) {
        return repository.findByInstanceId(id)
                .map(p -> {
                    Map<String, Object> body = new HashMap<>();
                    boolean ready = "ARTIFACTS_READY".equals(p.getStatus()) && p.getChangeName() != null && p.getChangePath() != null;
                    body.put("ready", ready);
                    body.put("changeName", p.getChangeName() != null ? p.getChangeName() : "");
                    body.put("changePath", p.getChangePath() != null ? p.getChangePath() : "");
                    body.put("message", ready
                            ? "在 Cursor 中运行 /opsx:apply 或指定变更名: " + p.getChangeName()
                            : "请先完成「生成设计」与「运行 openspec」步骤");
                    body.put("tasksPath", p.getChangePath() != null ? p.getChangePath() + "/tasks.md" : "");
                    return ResponseEntity.ok(body);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/context")
    public ResponseEntity<Map<String, String>> context(@PathVariable String id) {
        return repository.findByInstanceId(id)
                .map(p -> {
                    Map<String, String> body = new HashMap<>();
                    body.put("changeName", p.getChangeName() != null ? p.getChangeName() : "");
                    body.put("requirement", p.getRequirementText());
                    body.put("design", p.getDesignContent() != null ? p.getDesignContent() : "");
                    if (p.getChangePath() != null) {
                        try {
                            body.put("tasksMd", new String(Files.readAllBytes(Paths.get(p.getChangePath(), "tasks.md")), StandardCharsets.UTF_8));
                            body.put("proposalMd", new String(Files.readAllBytes(Paths.get(p.getChangePath(), "proposal.md")), StandardCharsets.UTF_8));
                            body.put("designMd", new String(Files.readAllBytes(Paths.get(p.getChangePath(), "design.md")), StandardCharsets.UTF_8));
                        } catch (Exception e) {
                            body.put("tasksMd", "");
                            body.put("proposalMd", "");
                            body.put("designMd", "");
                        }
                    } else {
                        body.put("tasksMd", "");
                        body.put("proposalMd", "");
                        body.put("designMd", "");
                    }
                    return ResponseEntity.ok(body);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
