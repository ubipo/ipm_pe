package net.pfiers.ipm_pe.service;

import net.pfiers.ipm_pe.dto.TaskDto;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface ITaskService {
    Collection<TaskDto> getAll();

    Optional<TaskDto> get(UUID uuid);

    TaskDto add(TaskDto task);

    Collection<TaskDto> subTasks(TaskDto parent);

    void clear();
}
