package net.pfiers.ipm_minor_ex1.service;

import net.pfiers.ipm_minor_ex1.dto.TaskDto;

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
