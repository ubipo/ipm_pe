package net.pfiers.ipm_pe.repo;

import net.pfiers.ipm_pe.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepo extends JpaRepository<Task, UUID> {
    Optional<Task> findByUuid(UUID uuid);

    List<Task> findAllByParentUuid(UUID uuid);
}
