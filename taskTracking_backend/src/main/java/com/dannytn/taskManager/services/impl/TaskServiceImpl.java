package com.dannytn.taskManager.services.impl;

import com.dannytn.taskManager.domain.entities.Task;
import com.dannytn.taskManager.domain.entities.TaskList;
import com.dannytn.taskManager.domain.entities.TaskPriority;
import com.dannytn.taskManager.domain.entities.TaskStatus;
import com.dannytn.taskManager.repositories.TaskListRepository;
import com.dannytn.taskManager.repositories.TaskRepository;
import com.dannytn.taskManager.services.TaskService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskListRepository taskListRepository;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskListRepository taskListRepository, TaskRepository taskRepository) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> listTasks(UUID taskListId) {
        return taskRepository.findByTaskListId(taskListId);
    }
    @Transactional
    @Override
    public Task createTask(UUID taskListId, Task task) {
        if (null != task.getId()){
            throw new IllegalArgumentException("Task already has an ID");
        }
        if (null == task.getTitle() || task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task title is empty");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus = TaskStatus.OPEN;

        TaskList taskList = taskListRepository
                .findById(taskListId)
                .orElseThrow( () -> new IllegalArgumentException("Invalid task list ID provided"));

        LocalDateTime now = LocalDateTime.now();
        Task taskToBeSaved = new Task(
                null,
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                taskPriority,
                taskStatus,
                taskList,
                now,
                now
        );
        return taskRepository.save(taskToBeSaved);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Transactional
    @Override
    public Task updateTask(UUID tasklistId, UUID taskId, Task task) {
        if (null == task.getId()){
            throw new IllegalArgumentException("Task must have an ID ");
        }
        if (!Objects.equals(task.getId(), taskId)){
            throw new IllegalArgumentException("Task IDs don't match");
        }
        if (null == task.getPriority()) {
            throw new IllegalArgumentException("Task must have a priority");
        }
        if(null == task.getStatus()) {
           throw new IllegalArgumentException("Task must have a status");
        }

        Task existingTask = taskRepository.findByTaskListIdAndId(tasklistId, taskId)
                .orElseThrow( () -> new IllegalArgumentException("Task not found"));

        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());

        return taskRepository.save(existingTask);
    }

    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
