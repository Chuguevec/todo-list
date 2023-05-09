package org.example.controler;

import org.example.domain.Status;
import org.example.domain.Task;
import org.example.dto.TaskDTO;
import org.example.service.TaskService;
import org.example.util.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAll(@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                         @RequestParam(value = "size", required = false, defaultValue = "10") Integer size, Model model) {
        List<Task> taskList = taskService.getAll(page, size);
        List<TaskDTO> taskDTOList = taskList.stream().map(DtoUtil::taskToTaskDTO).collect(Collectors.toList());
        model.addAttribute("taskList", taskDTOList);
        model.addAttribute("newTask", new TaskDTO());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("currentPageNumber", page);
        model.addAttribute("pageNumberList", taskService.getPageNumberList(size));
        return "/index";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", DtoUtil.taskToTaskDTO(taskService.getOne(id)));
        model.addAttribute("statuses", Status.values());
        return "/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("newTask") TaskDTO taskDTO, Model model) {
        taskService.create(DtoUtil.TaskDtoToTask(taskDTO));
        return getAll(1, 10, model);
    }

    @PatchMapping("/{id}")
    public String editTask(@PathVariable("id") int id,
                           @ModelAttribute("task") TaskDTO taskDTO, Model model) {
        Task task = DtoUtil.TaskDtoToTask(taskDTO);
        taskService.update(id, task);
        return getAll(1, 10, model);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") int id, @RequestParam(value = "page", required = false) Integer page, Model model) {
        taskService.delete(id);
        return getAll(page, 10, model);
    }
}
