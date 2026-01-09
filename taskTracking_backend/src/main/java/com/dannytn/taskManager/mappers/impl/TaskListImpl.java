package com.dannytn.taskManager.mappers.impl;

import com.dannytn.taskManager.domain.dto.TaskListDto;
import com.dannytn.taskManager.domain.entities.Task;
import com.dannytn.taskManager.domain.entities.TaskList;
import com.dannytn.taskManager.domain.entities.TaskStatus;
import com.dannytn.taskManager.mappers.TaskListMapper;
import com.dannytn.taskManager.mappers.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TaskListImpl implements TaskListMapper {
    private final TaskMapper taskMapper;

    public TaskListImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDto(TaskListDto dto) {
        return new TaskList(dto.id(),
                dto.title(),
                dto.description(),
                Optional.ofNullable(dto.tasks())
                        .map(tasks -> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList())
                        .orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDto(TaskList taskList) {
        return new TaskListDto(taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                calculateTasklistProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(taskDto -> taskDto.stream()
                                .map(taskMapper::toDto)
                                .toList())
                        .orElse(null));
    }

    public Double calculateTasklistProgress(List<Task> tasks) {
        if(tasks == null){
            return null;
        }

        long closedTasks = tasks.stream()
                .filter(task -> TaskStatus.CLOSED == task.getStatus())
                .count();

        return (double) closedTasks / tasks.size();
    }
}