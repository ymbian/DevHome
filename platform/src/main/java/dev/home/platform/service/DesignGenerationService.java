package dev.home.platform.service;

import dev.home.platform.config.PlatformProperties;
import dev.home.platform.domain.PipelineInstance;
import dev.home.platform.domain.PipelineInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Generates design document from requirement text.
 * Uses configurable template; optional LLM later.
 */
@Service
public class DesignGenerationService {

    private static final Logger log = LoggerFactory.getLogger(DesignGenerationService.class);
    private static final String DEFAULT_TEMPLATE_PATH = "design-template.md";

    private final PipelineInstanceRepository repository;
    private final PlatformProperties config;

    public DesignGenerationService(PipelineInstanceRepository repository, PlatformProperties config) {
        this.repository = repository;
        this.config = config;
    }

    /**
     * Generate design content for the pipeline instance and save it.
     * If LLM is configured, could call it here; else use template + requirement.
     */
    public String generate(String instanceId) {
        PipelineInstance p = repository.findByInstanceId(instanceId)
                .orElseThrow(() -> new IllegalArgumentException("Instance not found: " + instanceId));
        String template = loadTemplate();
        String content = template.replace("{{requirement}}", p.getRequirementText());
        if (config.getLlmEndpoint() != null && !config.getLlmEndpoint().isEmpty()) {
            content = generateWithLlm(p.getRequirementText(), content);
        }
        p.setDesignContent(content);
        p.setStatus("DESIGN_GENERATED");
        repository.save(p);
        log.info("Generated design for instance {}", instanceId);
        return content;
    }

    private String loadTemplate() {
        try {
            ClassPathResource res = new ClassPathResource(DEFAULT_TEMPLATE_PATH);
            try (InputStream is = res.getInputStream(); Scanner s = new Scanner(is, StandardCharsets.UTF_8.name()).useDelimiter("\\A")) {
                return s.hasNext() ? s.next() : getFallbackTemplate();
            }
        } catch (Exception e) {
            log.warn("Could not load design template, using fallback", e);
            return getFallbackTemplate();
        }
    }

    private String generateWithLlm(String requirement, String templateContent) {
        // Placeholder: LLM integration would call config.getLlmEndpoint().
        return templateContent;
    }

    private static String getFallbackTemplate() {
        return "# 需求\n\n{{requirement}}\n\n# 目标\n\n- 功能目标\n- 非功能目标\n\n# 设计要点\n\n- 待补充\n\n# 风险与开放问题\n\n- 待定\n";
    }
}
