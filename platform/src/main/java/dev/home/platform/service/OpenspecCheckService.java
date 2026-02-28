package dev.home.platform.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * Checks if openspec CLI is available (e.g. "openspec --version" or which).
 */
@Service
public class OpenspecCheckService {

    private static final String OPENSPEC_CMD = "openspec";
    private static final int TIMEOUT_SEC = 5;

    /**
     * @return true if openspec CLI runs successfully (e.g. exits 0 from "openspec --version").
     */
    public boolean isAvailable() {
        ProcessBuilder pb = new ProcessBuilder(OPENSPEC_CMD, "--version");
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            boolean finished = p.waitFor(TIMEOUT_SEC, TimeUnit.SECONDS);
            if (!finished) {
                p.destroyForcibly();
                return false;
            }
            return p.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return Version string from openspec --version, or null if not available.
     */
    public String getVersion() {
        ProcessBuilder pb = new ProcessBuilder(OPENSPEC_CMD, "--version");
        pb.redirectErrorStream(true);
        try {
            Process p = pb.start();
            try (BufferedReader r = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8))) {
                String line = r.readLine();
                boolean ok = p.waitFor(TIMEOUT_SEC, TimeUnit.SECONDS);
                if (!ok) p.destroyForcibly();
                return ok && p.exitValue() == 0 ? line : null;
            }
        } catch (Exception e) {
            return null;
        }
    }
}
