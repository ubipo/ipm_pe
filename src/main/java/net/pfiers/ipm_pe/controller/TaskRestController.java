package net.pfiers.ipm_pe.controller;

import net.pfiers.ipm_pe.dto.TaskDto;
import net.pfiers.ipm_pe.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
public class TaskRestController {
    private final TaskService service;

    @Autowired
    public TaskRestController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<TaskDto> getTasks() {
        return service.getAll();
    }

    @PostMapping
    public TaskDto postTask(@RequestBody @Valid TaskDto task) {
        return service.add(task);
    }

    @GetMapping("{uuid}")
    public TaskDto getTask(@PathVariable UUID uuid) {
        var task = service.get(uuid);
        if (task.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        return task.get();
    }

    @PutMapping("{uuid}")
    public ModelAndView getEditTask(@PathVariable UUID uuid) {
        var m = new ModelAndView("task-edit");
        var task = service.get(uuid);
        if (task.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        m.addObject("task", task.get());
        return m;
    }

    @PostMapping("{parentUuid}")
    public TaskDto postSubtask(@PathVariable UUID parentUuid, @ModelAttribute("task") @Valid TaskDto task) {
        var parentTask = service.get(parentUuid);
        if (parentTask.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        task.setParent(parentTask.get());
        return service.add(task);
    }

    @GetMapping("test-create")
    public RedirectView testCreateTask() {
        var dto = new TaskDto();
        dto.setDue(Instant.now());
        dto.setTitle("Do groceries");
        dto.setDescription("Task desc");
        service.add(dto);
        return new RedirectView("/tasks");
    }
}
