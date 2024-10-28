package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record UpdateTaskCommand(Long id, String title, String description) {
}
