package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence;

import com.greatnex.semicolon_task.application.ports.output.PlatformUserOutputPort;
import com.greatnex.semicolon_task.domain.models.PlatformUser;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository.PlatformUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Slf4j
public class UserPersistenceAdapterTest {
   @Autowired
    private UserPersistenceAdapter userPersistenceAdapter;

   private PlatformUser platformUser;

   @Autowired
    private PlatformUserRepository userRepository;

@BeforeEach
    void setUp() {
    platformUser = userPersistenceAdapter.save(
                  PlatformUser.builder()
            .id("3456778")
            .email("jonsnow@gmail.com")
            .name("John")
            .firstName("Wiz")
            .username("Jon")
            .middleName("Agent")
            .phoneNumber("2345787789")
             .dateOfBirth(LocalDate.now())
            .lastName("Snow")
            .emailVerified(true)
            .keycloakClientId("keycloak22345")
            .build());
}
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {StringUtils.EMPTY, " ", "undefined", "1234"})
    void getUserFullNameWithInvalidUserId(String userId){
        String userFullName = userPersistenceAdapter.getUserFullName(userId);
        assertNotNull(userFullName);
        assertEquals(StringUtils.EMPTY, userFullName);
    }
    @Test
    void getUserFullName(){
        String userFullName = userPersistenceAdapter.getUserFullName(platformUser.getId());
        assertNotNull(userFullName);
        log.info("{}", userFullName);
    }
    @Test
    void save(){
        try {
            assertNotNull(platformUser);
            assertNotNull(platformUser.getId());
            userRepository.deleteById(platformUser.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
