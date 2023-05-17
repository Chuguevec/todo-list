package org.example.controler;

import org.example.domain.Status;
import org.example.dto.TaskDTO;
import org.example.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        List<TaskDTO> taskDTOList = taskService.getAll(page, size);
        model.addAttribute("taskList", taskDTOList);
        model.addAttribute("newTask", new TaskDTO());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("currentPage", page);
        model.addAttribute("currentSize", size);
        model.addAttribute("pageCount", taskService.getCountPageBySize(size));
        return "/index";
    }

    @GetMapping("/{id}/edit")
    public String getEditPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("task", taskService.getById(id));
        model.addAttribute("statuses", Status.values());
        return "/edit";
    }

    @PostMapping
    public String create(@ModelAttribute("newTask") TaskDTO taskDTO, Model model) {
        taskService.create(taskDTO);
        return getAll(1, 10, model);
    }

    @PatchMapping("/{id}")
    public String editTask(@PathVariable("id") int id,
                           @ModelAttribute("task") TaskDTO taskDTO, Model model) {
        taskService.update(id, taskDTO);
        return getAll(1, 10, model);
    }

    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable("id") int id,
                             @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                             @RequestParam(value = "size", required = false, defaultValue = "10") Integer size, Model model) {
        taskService.delete(id);
        return getAll(page, size, model);
    }
}
