package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Task;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateTaskCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteTaskCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateTaskCommand;

import java.util.Optional;

public interface TaskCommandService {
    Long handle(CreateTaskCommand command);
    Optional<Task> handle(UpdateTaskCommand command);
    void handle(DeleteTaskCommand command);
}
