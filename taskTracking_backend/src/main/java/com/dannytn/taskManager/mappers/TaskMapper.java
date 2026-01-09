package com.dannytn.taskManager.mappers;

import com.dannytn.taskManager.domain.dto.TaskDto;
import com.dannytn.taskManager.domain.entities.Task;

public interface TaskMapper {
    Task fromDto(TaskDto dto);
    TaskDto toDto(Task task);
}
