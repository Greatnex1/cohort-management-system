package com.greatnex.semicolon_task.infrastructure.adapters.config.allowedhost;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("touch")
public class DefaultHost implements AllowedHost{

    String[] methods = {"GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"};

    @Override
    public String[] getPatterns() {
        return new String[]{"http://localhost:3000", "http://localhost:3000/", "http://localhost:5001/", "http://localhost:5001"};
    }

    @Override
    public String[] getMethods() {
        return methods;
    }
}
