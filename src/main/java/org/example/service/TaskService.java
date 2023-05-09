package org.example.service;

import org.example.domain.Task;

import java.util.List;

public interface TaskService {
    Task getOne(int id);

    List<Task> getAll(Integer page, Integer size);

    List<Integer> getPageNumberList(int size);

    void create(Task task);

    Task update(int id, Task task);

    void delete(int id);
}
