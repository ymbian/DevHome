package dev.home.platform.web;

import dev.home.platform.domain.PipelineInstance;
import dev.home.platform.domain.PipelineInstanceRepository;
import dev.home.platform.service.DesignGenerationService;
import dev.home.platform.service.OpenspecService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/instances/{id}")
public class DesignController {

    private static ResponseEntity<Void> notFoundVoid() {
        @SuppressWarnings("unchecked")
        ResponseEntity<Void> r = (ResponseEntity<Void>) (ResponseEntity<?>) ResponseEntity.notFound().build();
        return r;
    }

    private static ResponseEntity<Map<String, String>> notFoundMap() {
        @SuppressWarnings("unchecked")
        ResponseEntity<Map<String, String>> r = (ResponseEntity<Map<String, String>>) (ResponseEntity<?>) ResponseEntity.notFound().build();
        return r;
    }

    private final PipelineInstanceRepository repository;
    private final DesignGenerationService designService;
    private final OpenspecService openspecService;

    public DesignController(PipelineInstanceRepository repository,
                            DesignGenerationService designService,
                            OpenspecService openspecService) {
        this.repository = repository;
        this.designService = designService;
        this.openspecService = openspecService;
    }

    @GetMapping("/design")
    public ResponseEntity<Map<String, String>> getDesign(@PathVariable String id) {
        return repository.findByInstanceId(id)
                .map(p -> ResponseEntity.ok(Collections.singletonMap("content", p.getDesignContent() != null ? p.getDesignContent() : "")))
                .orElse(notFoundMap());
    }

    @PutMapping("/design")
    @SuppressWarnings("unchecked")
    public ResponseEntity<?> saveDesign(@PathVariable String id, @RequestBody Map<String, String> body) {
        String content = body != null ? body.get("content") : null;
        Optional<PipelineInstance> opt = repository.findByInstanceId(id);
        if (!opt.isPresent()) {
            return (ResponseEntity<?>) notFoundVoid();
        }
        PipelineInstance p = opt.get();
        p.setDesignContent(content != null ? content : "");
        repository.save(p);
        return (ResponseEntity<?>) ResponseEntity.ok().build();
    }

    /**
     * 自动获取 Git 仓库地址（占位：后端逻辑暂未实现）。
     */
    @GetMapping("/git-url")
    public ResponseEntity<Map<String, String>> getGitUrl(@PathVariable String id) {
        if (!repository.findByInstanceId(id).isPresent()) {
            return notFoundMap();
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Collections.singletonMap("message", "自动获取 Git 地址功能开发中"));
    }

    /**
     * 将设计内容保存到指定 Git 仓库（占位：推送逻辑暂未实现）。
     */
    @PostMapping("/design/save-to-git")
    public ResponseEntity<Map<String, String>> saveDesignToGit(@PathVariable String id, @RequestBody Map<String, String> body) {
        if (!repository.findByInstanceId(id).isPresent()) {
            return notFoundMap();
        }
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(Collections.singletonMap("message", "保存到 Git 功能开发中"));
    }

    @PostMapping("/design/generate")
    public ResponseEntity<Map<String, String>> generateDesign(@PathVariable String id,
                                                              @RequestParam(name = "specMode", required = false, defaultValue = "false") boolean specMode) {
        return repository.findByInstanceId(id)
                .map(p -> {
                    try {
                        String content = designService.generate(id);
                        p.setSpecModeEnabled(specMode);
                        repository.save(p);
                        Map<String, String> body = new HashMap<>();
                        body.put("content", content);
                        if (specMode) {
                            String changePath = openspecService.runOpenspec(id);
                            body.put("specGenerated", "true");
                            body.put("changePath", changePath != null ? changePath : "");
                        } else {
                            body.put("specGenerated", "false");
                        }
                        return ResponseEntity.ok(body);
                    } catch (IllegalArgumentException e) {
                        return notFoundMap();
                    } catch (Exception e) {
                        p.setStatus("FAILED");
                        repository.save(p);
                        Map<String, String> err = new HashMap<>();
                        err.put("content", "");
                        err.put("error", e.getMessage());
                        return ResponseEntity.status(500).body(err);
                    }
                })
                .orElse(notFoundMap());
    }
}
