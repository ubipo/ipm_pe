package net.pfiers.ipm_minor_ex1.service;

import net.pfiers.ipm_minor_ex1.domain.Task;
import net.pfiers.ipm_minor_ex1.dto.TaskDto;
import net.pfiers.ipm_minor_ex1.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService implements ITaskService {
    private final TaskRepo repo;

    @Autowired
    public TaskService(TaskRepo repo) {
        this.repo = repo;
    }

    @Override
    public Collection<TaskDto> getAll() {
        return repo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<TaskDto> get(UUID uuid) {
        return repo.findByUuid(uuid).map(this::toDto);
    }

    @Override
    public TaskDto add(TaskDto dto) {
        var baseTask = new Task();
        if (dto.getUuid() != null) {
            var oldTask = repo.findByUuid(dto.getUuid());
            if (oldTask.isEmpty())
                throw new NoSuchElementException(String.format("no task with uuid=%s", dto.getUuid()));
            baseTask = oldTask.get();
        }
        var task = updateTask(baseTask, dto);
        return toDto(repo.save(task));
    }

    @Override
    public Collection<TaskDto> subTasks(TaskDto parent) {
        return repo.findAllByParentUuid(parent.getUuid()).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        repo.deleteAll();
    }


    private TaskDto toDto(Task task) {
        return updateDto(new TaskDto(), task);
    }

    private Task toTask(TaskDto dto) {
        return updateTask(new Task(), dto);
    }

    private TaskDto updateDto(TaskDto dto, Task task) {
        dto.setUuid(task.getUuid());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDue(task.getDue());
        var parent = task.getParent();
        if (parent != null)
            dto.setParent(toDto(parent));
        return dto;
    }

    private Task updateTask(Task task, TaskDto dto) {
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDue(dto.getDue());
        var parent = dto.getParent();
        if (parent != null) {
            var parentTask = repo.findByUuid(parent.getUuid());
            if (parentTask.isEmpty())
                throw new NoSuchElementException(String.format("no task with uuid=%s", parent.getUuid()));
            task.setParent(parentTask.get());
        }
        return task;
    }
}
