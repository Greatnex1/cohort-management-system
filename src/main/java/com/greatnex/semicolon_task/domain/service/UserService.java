package com.greatnex.semicolon_task.domain.service;

import com.greatnex.semicolon_task.application.ports.input.PlatformUserUseCase;
import com.greatnex.semicolon_task.application.ports.output.GetUserFullNameOutputPort;
import com.greatnex.semicolon_task.application.ports.output.PlatformUserOutputPort;
import com.greatnex.semicolon_task.domain.messages.ErrorMessages;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@RequiredArgsConstructor
public class UserService implements PlatformUserUseCase{
    private final GetUserFullNameOutputPort getUserNameOutputPort;
}
