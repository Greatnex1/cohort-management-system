package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.mapper;

import com.greatnex.semicolon_task.domain.models.PlatformUser;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.PlatformUserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PlatformUserMapper {
    PlatformUserEntity toPlatformUserEntity(PlatformUser user);
    PlatformUser toPlatformUser(PlatformUserEntity userEntity);

}
