package com.greatnex.semicolon_task.application.ports.output;

import com.greatnex.semicolon_task.domain.models.PlatformUser;

public interface PlatformUserOutputPort {
    void save(PlatformUser platformUser);
}
