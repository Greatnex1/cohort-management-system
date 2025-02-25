package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence;

import com.greatnex.semicolon_task.application.ports.output.GetUserFullNameOutputPort;
import com.greatnex.semicolon_task.application.ports.output.PlatformUserOutputPort;
//import com.greatnex.semicolon_task.domain.exception.UserException;
import com.greatnex.semicolon_task.domain.exception.UserException;
import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.dao.UserName;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.PlatformUserEntity;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.mapper.PlatformUserMapper;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository.PlatformUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.keycloak.events.Errors;
import org.springframework.boot.web.server.Http2;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserPersistenceAdapter implements GetUserFullNameOutputPort, PlatformUserOutputPort {

    private final PlatformUserRepository userRepository;
    private final PlatformUserMapper platformUserMapper;

    @Override
    public PlatformUser save(PlatformUser platformUser) {
        log.info("Saving user identity {}", platformUser);
        platformUser.validateUserRequiredData();
   PlatformUserEntity userEntity = platformUserMapper.toPlatformUserEntity(platformUser);
    userRepository.save(userEntity);
        log.info("Saved user identity {}",platformUser);
     return platformUserMapper.toPlatformUser(userEntity);
    }

    @Override
    public PlatformUser findUserById(String id) throws UserException {
        if (StringUtils.isEmpty(id)) {
            PlatformUserEntity foundUser = userRepository.findById(id).orElseThrow(() -> new UserException(
                    ErrorMessages.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
            return platformUserMapper.toPlatformUser(foundUser);
        }
            throw new UserException(String.format(ErrorMessages.NULL_OBJECT));
    }

    @Override
    public String getUserFullName(String id) {
        if (StringUtils.isNotBlank(id) || StringUtils.isNotEmpty(id) || id != null){
            Optional<UserName> userName = userRepository.findUserNameById(id);
            return userName.map(UserName::getFullName).orElse(StringUtils.EMPTY);
        }
        return StringUtils.EMPTY;
    }

    @Override
    public boolean userExists(String id) {
        return userRepository.existsById(id);
    }
}
