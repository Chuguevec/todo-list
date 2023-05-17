package org.example.service;

import org.example.dao.TaskDAO;
import org.example.domain.Task;
import org.example.dto.TaskDTO;
import org.example.util.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDAO taskDAO;

    @Autowired
    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public TaskDTO getById(int id) {
        Task task = taskDAO.getById(id).get();
        return DtoUtil.taskToTaskDTO(task);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDTO> getAll(Integer page, Integer size) {
        List<Task> tasks = taskDAO.getAll(page, size);
        return tasks.stream().map(DtoUtil::taskToTaskDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getCountPageBySize(int size) {
        //получаем колличество тасков
        int taskCount = Math.toIntExact(taskDAO.getTaskCount());
        //получаем количество страниц
        int pageCount = taskCount / size;
        //увеличиваем количество страниц на 1, если остаток от деление больше 0
        if (taskCount % size != 0) {
            pageCount++;
        }
        return pageCount;
    }

    @Override
    @Transactional
    public void create(TaskDTO taskDTO) {
        Task task = DtoUtil.TaskDtoToTask(taskDTO);
        taskDAO.create(task);
    }

    @Override
    @Transactional
    public Task update(int id, TaskDTO updatedTaskDto) {
        Task taskToUpdate = taskDAO.getById(id).get();
        taskToUpdate.setDescription(updatedTaskDto.getDescription());
        taskToUpdate.setStatus(updatedTaskDto.getStatus());
        return taskDAO.update(taskToUpdate).get(); //Излишнее действие
    }

    @Override
    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id).get();
        taskDAO.delete(task);
    }
}
