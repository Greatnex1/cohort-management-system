package com.greatnex.semicolon_task.application.ports.output;


import com.greatnex.semicolon_task.domain.models.PlatformUser;

public interface GetUserFullNameOutputPort {
 void save(PlatformUser platformUser);
   String getUserFullName(String id);
   boolean userExists(String id);
}
