package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence;

import com.greatnex.semicolon_task.application.ports.output.GetUserFullNameOutputPort;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.dao.UserName;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository.PlatformUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserPersistenceAdapter implements GetUserFullNameOutputPort {

    private final PlatformUserRepository userRepository;
    @Override
    public void save(PlatformUser platformUser) {

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
