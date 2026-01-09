package com.dannytn.taskManager.services;

import com.dannytn.taskManager.domain.entities.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<Task> listTasks(UUID taskListId);
    Task createTask(UUID taskListId, Task task);
    Optional <Task> getTask(UUID taskListId, UUID taskId);
    Task updateTask(UUID tasklistId, UUID taskId, Task task);
    void deleteTask(UUID taskListId, UUID taskId);
}
