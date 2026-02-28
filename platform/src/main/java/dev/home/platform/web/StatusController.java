package dev.home.platform.web;

import dev.home.platform.config.PlatformProperties;
import dev.home.platform.service.OpenspecCheckService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Exposes openspec CLI availability and platform config (for UI or startup check).
 */
@RestController
@RequestMapping("/api")
public class StatusController {

    private final OpenspecCheckService openspecCheck;
    private final PlatformProperties config;

    public StatusController(OpenspecCheckService openspecCheck, PlatformProperties config) {
        this.openspecCheck = openspecCheck;
        this.config = config;
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> body = new HashMap<>();
        body.put("openspecAvailable", openspecCheck.isAvailable());
        body.put("openspecVersion", openspecCheck.getVersion());
        body.put("openspecWorkDir", config.getOpenspecWorkDir());
        body.put("llmConfigured", config.getLlmEndpoint() != null && !config.getLlmEndpoint().isEmpty());
        body.put("cursorApiConfigured", config.getCursorApiEndpoint() != null && !config.getCursorApiEndpoint().isEmpty());
        return ResponseEntity.ok(body);
    }
}
