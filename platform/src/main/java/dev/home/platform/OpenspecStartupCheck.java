package dev.home.platform;

import dev.home.platform.service.OpenspecCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class OpenspecStartupCheck implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(OpenspecStartupCheck.class);

    private final OpenspecCheckService openspecCheck;

    public OpenspecStartupCheck(OpenspecCheckService openspecCheck) {
        this.openspecCheck = openspecCheck;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (openspecCheck.isAvailable()) {
            log.info("openspec CLI: available ({})", openspecCheck.getVersion());
        } else {
            log.warn("openspec CLI: not available (install or set PATH). Pipeline openspec steps will fail.");
        }
    }
}
