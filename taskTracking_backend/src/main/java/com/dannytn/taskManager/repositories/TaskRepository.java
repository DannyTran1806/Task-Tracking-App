package com.dannytn.taskManager.repositories;

import com.dannytn.taskManager.domain.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByTaskListId(UUID tasklistId);
    Optional<Task> findByTaskListIdAndId(UUID tasklistId, UUID id);
    void deleteByTaskListIdAndId(UUID tasklistId, UUID id);
}
