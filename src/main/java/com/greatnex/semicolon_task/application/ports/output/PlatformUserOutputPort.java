package com.greatnex.semicolon_task.application.ports.output;

//import com.greatnex.semicolon_task.domain.exception.UserException;
import com.greatnex.semicolon_task.domain.exception.UserException;
import com.greatnex.semicolon_task.domain.models.PlatformUser;

public interface PlatformUserOutputPort {
    PlatformUser save (PlatformUser platformUser);
   PlatformUser findUserById(String id) throws UserException;
    boolean userExists(String id);
}
