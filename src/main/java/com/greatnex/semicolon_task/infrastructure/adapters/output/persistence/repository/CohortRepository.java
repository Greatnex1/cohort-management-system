package com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.repository;

import com.greatnex.semicolon_task.domain.models.Cohort;
import com.greatnex.semicolon_task.infrastructure.adapters.output.persistence.entity.CohortEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;


public interface CohortRepository extends JpaRepository<CohortEntity, String> {

   boolean existsByName(String name);
}
