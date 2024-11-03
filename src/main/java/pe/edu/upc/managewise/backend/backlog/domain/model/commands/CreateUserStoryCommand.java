package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record CreateUserStoryCommand(String title, String description, Long epicId, Long sprintId, Integer effort) {
}
