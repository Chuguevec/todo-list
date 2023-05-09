package org.example.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.example.dao.TaskDAO;
import org.example.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{

    private final TaskDAO taskDAO;

    @Autowired
    public TaskServiceImpl(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public Task getOne(int id) {
        return taskDAO.findOne(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAll(Integer page, Integer size) {
        return taskDAO.findAll(page, size);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> getPageNumberList(int size) {
        //получаем колличество тасков
        int taskCount = Math.toIntExact(taskDAO.getTaskCount());
        //получаем количество страниц
        long pageCount = taskCount/size;
        //увеличиваем количество страниц на 1, если остаток от деление больше 0
        if(taskCount%size != 0){
            pageCount++;
        }
        //создаем список номеров страниц
        List<Integer> pagesCount = new ArrayList<>();
        for (int i = 1; i <= pageCount; i++) {
            pagesCount.add(i);
        }
        return pagesCount;
    }

    @Override
    @Transactional
    public void create(Task task) {
        taskDAO.create(task);
    }

    @Override
    @Transactional
    public Task update(int id, Task updatedTask) {
        Task taskToUpdate = taskDAO.findOne(id).get();
        taskToUpdate.setDescription(updatedTask.getDescription());
        taskToUpdate.setStatus(updatedTask.getStatus());
        return taskDAO.update(taskToUpdate).get(); //Излишнее действие
    }

    @Override
    @Transactional
    public void delete(int id) {
        Task task = taskDAO.findOne(id).get();
        taskDAO.delete(task);
    }
}
