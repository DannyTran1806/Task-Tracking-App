package com.dannytn.taskManager.domain.dto;

public record DefaultErrorResponse(
        int status,
        String message,
        String details
) { }
