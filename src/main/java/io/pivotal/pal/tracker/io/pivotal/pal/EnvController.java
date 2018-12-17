package io.pivotal.pal.tracker.io.pivotal.pal;

import java.util.HashMap;
import java.util.Map;

public class EnvController {

    private Map<String,String > env;

    public EnvController(String port, String memLimit, String instIndex, String instADDR) {
        env = new HashMap<>();
        env.put("PORT", port);
        env.put("MEMORY_LIMIT", memLimit);
        env.put("CF_INSTANCE_INDEX", instIndex);
        env.put("CF_INSTANCE_ADDR", instADDR);
    }


    public Map<String, String> getEnv() {
        return this.env;
    }
}
