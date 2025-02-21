package com.greatnex.semicolon_task.domain.service;

import com.greatnex.semicolon_task.application.ports.output.GetUserFullNameOutputPort;
import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
@Log4j2
//@Slf4j
public class UserService implements GetUserFullNameOutputPort {

    private final GetUserFullNameOutputPort getUserFullNameOutputPort;
    @Override
    public void save(PlatformUser platformUser) {
        if(ObjectUtils.isEmpty(platformUser)){
            throw new IllegalArgumentException(String.format(ErrorMessages.NULL_OBJECT));
        }
        platformUser.validateUserRequiredData();


//log.info("Saving user: {}", platformUser);
//log.info("Saving user first name: {}", platformUser.getName());
    }

    @Override
    public String getUserFullName(String id) {
        return "";
    }

    @Override
    public boolean userExists(String id) {
        return false;
    }
}
