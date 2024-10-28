package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record CreateTaskCommand(Long userStoryId, String name, String description ) {
}
