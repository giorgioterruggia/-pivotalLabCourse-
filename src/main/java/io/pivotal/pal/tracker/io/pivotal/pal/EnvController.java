package io.pivotal.pal.tracker.io.pivotal.pal;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private Map<String,String > env;

    public EnvController(@Value("${PORT:NOT SET}") String port,
                         @Value("${MEMORY_LIMIT:NOT SET}") String memLimit,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String instIndex,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}")String instADDR) {
        env = new HashMap<>();
        env.put("PORT", port);
        env.put("MEMORY_LIMIT", memLimit);
        env.put("CF_INSTANCE_INDEX", instIndex);
        env.put("CF_INSTANCE_ADDR", instADDR);
    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        return this.env;
    }
}
