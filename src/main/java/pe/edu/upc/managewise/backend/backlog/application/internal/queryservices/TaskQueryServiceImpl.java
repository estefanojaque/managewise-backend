package pe.edu.upc.managewise.backend.backlog.application.internal.queryservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Task;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllTasksQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetTaskByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.TaskQueryService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TaskQueryServiceImpl implements TaskQueryService {
    private final TaskRepository taskRepository;

    public TaskQueryServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> handle(GetAllTasksQuery query){
        return this.taskRepository.findAll();
    }

    @Override
    public Optional<Task> handle(GetTaskByIdQuery query){
        return this.taskRepository.findById(query.taskId());
    }

}
