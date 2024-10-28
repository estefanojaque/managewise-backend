package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Task;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllTasksQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetTaskByIdQuery;

import java.util.List;
import java.util.Optional;

public interface TaskQueryService{
    List<Task> handle(GetAllTasksQuery query);
    Optional<Task> handle(GetTaskByIdQuery query);
}
