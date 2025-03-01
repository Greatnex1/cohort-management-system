package com.greatnex.semicolon_task.infrastructure.adapters.config.allowedhost;

public interface AllowedHost {
    String[] getPatterns();
    String[] getMethods();
}
