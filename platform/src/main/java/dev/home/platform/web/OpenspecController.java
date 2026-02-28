package dev.home.platform.web;

import dev.home.platform.domain.PipelineInstanceRepository;
import dev.home.platform.service.OpenspecService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/instances/{id}")
public class OpenspecController {

    private final PipelineInstanceRepository repository;
    private final OpenspecService openspecService;

    public OpenspecController(PipelineInstanceRepository repository, OpenspecService openspecService) {
        this.repository = repository;
        this.openspecService = openspecService;
    }

    @PostMapping("/openspec/run")
    public ResponseEntity<Map<String, String>> runOpenspec(@PathVariable String id) {
        return repository.findByInstanceId(id)
                .map(p -> {
                    try {
                        String changePath = openspecService.runOpenspec(id);
                        PipelineInstance updated = repository.findByInstanceId(id).orElse(p);
                        return ResponseEntity.ok(Map.of(
                                "changeName", updated.getChangeName() != null ? updated.getChangeName() : "",
                                "changePath", changePath));
                    } catch (Exception e) {
                        repository.findByInstanceId(id).ifPresent(inst -> {
                            inst.setStatus("FAILED");
                            repository.save(inst);
                        });
                        return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
