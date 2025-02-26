package com.greatnex.semicolon_task.application.ports.output;


import com.greatnex.semicolon_task.domain.models.PlatformUser;

public interface GetUserFullNameOutputPort {
    String getUserFullName(String id);

}
