package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository;

import com.greatnex.semicolon_task.domain.models.PlatformUser;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.dao.UserName;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.PlatformUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


public  interface PlatformUserRepository extends JpaRepository<PlatformUserEntity, String> {
@Query("SELECT user.id as id, user.firstName as firstName, user.lastName as lastName from PlatformUserEntity user where user.id = :userId")
    Optional<UserName> findUserNameById(@Param("userId") String userId);

}
