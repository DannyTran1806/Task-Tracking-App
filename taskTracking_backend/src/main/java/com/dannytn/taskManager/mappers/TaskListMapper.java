package com.dannytn.taskManager.mappers;

import com.dannytn.taskManager.domain.dto.TaskListDto;
import com.dannytn.taskManager.domain.entities.TaskList;

public interface TaskListMapper {
    TaskList fromDto (TaskListDto taskListDto);
    TaskListDto toDto(TaskList taskList);
}
