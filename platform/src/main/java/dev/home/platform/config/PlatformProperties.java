package dev.home.platform.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Platform configuration: openspec work dir, optional LLM and Cursor API.
 * Bind from application.properties or env (e.g. PLATFORM_OPENSPEC_WORK_DIR).
 */
@Component
@ConfigurationProperties(prefix = "platform")
public class PlatformProperties {

    /** Openspec working directory (repo root where openspec is run). */
    private String openspecWorkDir = ".";

    /** Optional LLM API endpoint for design generation. */
    private String llmEndpoint;

    /** Optional Cursor API endpoint for triggering coding. */
    private String cursorApiEndpoint;

    public String getOpenspecWorkDir() {
        return openspecWorkDir;
    }

    public void setOpenspecWorkDir(String openspecWorkDir) {
        this.openspecWorkDir = openspecWorkDir;
    }

    public String getLlmEndpoint() {
        return llmEndpoint;
    }

    public void setLlmEndpoint(String llmEndpoint) {
        this.llmEndpoint = llmEndpoint;
    }

    public String getCursorApiEndpoint() {
        return cursorApiEndpoint;
    }

    public void setCursorApiEndpoint(String cursorApiEndpoint) {
        this.cursorApiEndpoint = cursorApiEndpoint;
    }
}
