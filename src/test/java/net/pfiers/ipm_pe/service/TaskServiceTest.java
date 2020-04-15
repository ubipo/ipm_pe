package net.pfiers.ipm_pe.service;

import net.pfiers.ipm_pe.dto.TaskDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    private ITaskService taskService;

    @BeforeEach
    public void beforeEach() {
        taskService.clear();
    }

    @Test
    void getAll() {
        var taskA = TaskDto.mockObj();
        taskA.setUuid(taskService.add(taskA).getUuid()); // UUID is generated in Task
        var taskB = TaskDto.mockObj();
        taskB.setUuid(taskService.add(taskB).getUuid());
        var taskC = TaskDto.mockObj();
        taskC.setUuid(taskService.add(taskC).getUuid());
        assertEquals(List.of(taskA, taskB, taskC), taskService.getAll());
    }

    @Test
    void addGet() {
        var task = TaskDto.mockObj();
        var uuid = taskService.add(task).getUuid();
        task.setUuid(uuid);
        assertEquals(task, taskService.get(uuid).orElse(null));
    }

    @Test
    void addUpdateGet() {
        var task = TaskDto.mockObj();
        var uuid = taskService.add(task).getUuid();
        task.setUuid(uuid);
        task.setTitle("A different title");
        taskService.add(task);
        assertEquals(task, taskService.get(uuid).orElse(null));
    }

    @Test
    void updateNoSuchElement() {
        var task = TaskDto.mockObj();
        task.setUuid(UUID.randomUUID());
        assertThrows(NoSuchElementException.class, () -> {
            taskService.add(task);
        });
    }

    @Test
    void subTasks() {
        var task = TaskDto.mockObj();
        var subTask = TaskDto.mockObj();
        subTask.setParent(task);
        task.setUuid(taskService.add(task).getUuid());
        subTask.setUuid(taskService.add(subTask).getUuid());
        assertEquals(List.copyOf(Collections.singleton(subTask)), List.copyOf(taskService.subTasks(task)));
    }
}