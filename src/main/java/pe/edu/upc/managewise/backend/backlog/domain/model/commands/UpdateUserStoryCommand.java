package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record UpdateUserStoryCommand(Long id, String title, String description) {
}
