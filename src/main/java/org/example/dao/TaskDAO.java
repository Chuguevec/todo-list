package org.example.dao;


import org.example.domain.Task;

import java.util.List;
import java.util.Optional;

public interface TaskDAO {

    Optional<Task> getById(int id);
    List<Task> getAll(int page, int size);
    Long getTaskCount();
    void create(Task task);
    Optional<Task> update(Task task);
    void delete(Task task);

}
