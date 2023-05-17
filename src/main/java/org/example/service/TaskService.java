package org.example.service;

import org.example.domain.Task;
import org.example.dto.TaskDTO;

import java.util.List;

public interface TaskService {
    TaskDTO getById(int id);

    List<TaskDTO> getAll(Integer page, Integer size);

    Integer getCountPageBySize(int size);

    void create(TaskDTO taskDTO);

    Task update(int id, TaskDTO taskDTO);

    void delete(int id);
}
