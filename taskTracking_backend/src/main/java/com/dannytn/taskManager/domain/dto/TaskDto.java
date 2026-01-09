package com.dannytn.taskManager.domain.dto;

import com.dannytn.taskManager.domain.entities.TaskPriority;
import com.dannytn.taskManager.domain.entities.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
)
{ }
