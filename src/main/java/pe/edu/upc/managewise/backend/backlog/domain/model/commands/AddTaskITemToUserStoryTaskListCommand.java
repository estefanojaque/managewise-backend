package pe.edu.upc.managewise.backend.backlog.domain.model.commands;

public record AddTaskITemToUserStoryTaskListCommand(String title, String description, Integer estimation, Long userStoryId) {
}
