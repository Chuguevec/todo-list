package org.example.util;

import org.example.domain.Status;
import org.example.domain.Task;
import org.example.dto.TaskDTO;

public class DtoUtil {

    public static Task TaskDtoToTask(TaskDTO taskDTO){
        Task task = new Task();
        task.setStatus(taskDTO.getStatus());
        task.setDescription(taskDTO.getDescription());
        return task;
    }

    public static TaskDTO taskToTaskDTO(Task task){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setDescription(task.getDescription());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setId(task.getId());
        return taskDTO;
    }
}
