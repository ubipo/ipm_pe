package net.pfiers.ipm_pe.controller;

import net.pfiers.ipm_pe.dto.TaskDto;
import net.pfiers.ipm_pe.service.TaskService;
import net.pfiers.ipm_pe.util.SlugUUIDFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView getTasks() {
        var m = new ModelAndView("task/tasks");
        m.addObject("tasks", service.getAll());
        return m;
    }

    @GetMapping("{uuid}")
    public ModelAndView getTask(@PathVariable UUID uuid) {
        var m = new ModelAndView("task/task");
        var task = service.get(uuid);
        if (task.isEmpty())
            throw new NoSuchTaskException(uuid);
        m.addObject("task", task.get());
        var subTasks = service.subTasks(task.get());
        m.addObject("subTasks", subTasks);
        return m;
    }

    @GetMapping("create")
    public ModelAndView getCreate() {
        var m = new ModelAndView("task/task-create");
        m.addObject("task", new TaskDto());
        return m;
    }

    @PostMapping("create")
    @Secured({"ROLE_ADMIN"})
    public ModelAndView postTask(ModelMap model, @ModelAttribute("task") @Valid TaskDto task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var m = new ModelAndView("task/task-create", model);
            m.addObject("task", task);
            m.setStatus(HttpStatus.BAD_REQUEST);
            return m;
        }
        task = service.add(task);
        return new ModelAndView(new RedirectView(task.getSlug(), true));
    }

    @GetMapping("edit/{uuid}")
    public ModelAndView getEditTask(@PathVariable UUID uuid) {
        var m = new ModelAndView("task/task-edit");
        var task = service.get(uuid);
        if (task.isEmpty())
            throw new NoSuchTaskException(uuid);

        m.addObject("task", task.get());
        return m;
    }

    @PostMapping("edit/{uuid}")
    @Secured({"ROLE_ADMIN"})
    public ModelAndView postEditTask(ModelMap model, @PathVariable UUID uuid, @ModelAttribute("task") @Valid TaskDto task, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getAllErrors().stream().map(ObjectError::toString).collect(Collectors.joining()));
            var m = new ModelAndView("task/task-edit", model);
            task.setUuid(uuid);
            m.addObject("task", task);
            m.setStatus(HttpStatus.BAD_REQUEST);
            return m;
        }
        task.setUuid(uuid);
        task = service.add(task);
        return new ModelAndView(new RedirectView(String.format("/tasks/%s", SlugUUIDFormatter.print(task.getUuid()))));
    }

    @GetMapping("{parentUuid}/sub/create")
    public ModelAndView getCreateSubtask(@PathVariable UUID parentUuid) {
        var m = new ModelAndView("task/task-sub-create");
        var parentTask = service.get(parentUuid);
        if (parentTask.isEmpty())
            throw new NoSuchTaskException(parentUuid);

        m.addObject("parentTask", parentTask.get());
        m.addObject("task", new TaskDto());
        return m;
    }

    @PostMapping("{parentUuid}/sub/create")
    @Secured({"ROLE_ADMIN"})
    public ModelAndView postSubtask(@PathVariable UUID parentUuid, ModelMap model, @ModelAttribute("task") @Valid TaskDto task, BindingResult bindingResult) {
        var parentTask = service.get(parentUuid);
        if (parentTask.isEmpty())
            throw new NoSuchTaskException(parentUuid);

        if (bindingResult.hasErrors()) {
            var m = new ModelAndView("task/task-sub-create", model);
            m.addObject("parentTask", parentTask.get());
            m.addObject("task", task);
            m.setStatus(HttpStatus.BAD_REQUEST);
            return m;
        }
        task.setParent(parentTask.get());
        task = service.add(task);
        return new ModelAndView(new RedirectView(String.format("/tasks/%s", SlugUUIDFormatter.print(task.getUuid()))));
    }

    @ExceptionHandler(NoSuchTaskException.class)
    public ModelAndView noSuchTask(NoSuchTaskException ex) {
        var m = new ModelAndView("task/err/no-task");
        m.addObject("uuid", ex.uuid);
        m.setStatus(HttpStatus.NOT_FOUND);
        return m;
    }


    @GetMapping("test-create")
    public RedirectView testCreateTask() {
        var dto = TaskDto.mockObj();
        service.add(dto);
        return new RedirectView("/tasks");
    }

    @GetMapping("{parentUuid}/sub/test-create")
    public RedirectView testCreateSubtask(@PathVariable UUID parentUuid) {
        var parentTask = service.get(parentUuid);
        if (parentTask.isEmpty())
            throw new NoSuchTaskException(parentUuid);

        var dto = TaskDto.mockObj();
        dto.setTitle("Subtask title");
        dto.setParent(parentTask.get());
        service.add(dto);
        return new RedirectView("/tasks");
    }
}
