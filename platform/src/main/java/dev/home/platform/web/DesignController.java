package dev.home.platform.web;

import dev.home.platform.domain.PipelineInstanceRepository;
import dev.home.platform.service.DesignGenerationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/instances/{id}")
public class DesignController {

    private final PipelineInstanceRepository repository;
    private final DesignGenerationService designService;

    public DesignController(PipelineInstanceRepository repository, DesignGenerationService designService) {
        this.repository = repository;
        this.designService = designService;
    }

    @GetMapping("/design")
    public ResponseEntity<Map<String, String>> getDesign(@PathVariable String id) {
        return repository.findByInstanceId(id)
                .map(p -> ResponseEntity.ok(Map.of("content", p.getDesignContent() != null ? p.getDesignContent() : "")))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/design")
    public ResponseEntity<Void> saveDesign(@PathVariable String id, @RequestBody Map<String, String> body) {
        String content = body != null ? body.get("content") : null;
        return repository.findByInstanceId(id)
                .map(p -> {
                    p.setDesignContent(content != null ? content : "");
                    repository.save(p);
                    return ResponseEntity.<Void>ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/design/generate")
    public ResponseEntity<Map<String, String>> generateDesign(@PathVariable String id) {
        return repository.findByInstanceId(id)
                .map(p -> {
                    try {
                        String content = designService.generate(id);
                        return ResponseEntity.ok(Map.of("content", content));
                    } catch (IllegalArgumentException e) {
                        return ResponseEntity.notFound().build();
                    } catch (Exception e) {
                        p.setStatus("FAILED");
                        repository.save(p);
                        return ResponseEntity.status(500).body(Map.of("content", "", "error", e.getMessage()));
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
