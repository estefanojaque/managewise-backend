package pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources;

import java.util.List;

public record UserStoryResource(
        Long id,
        String title,
        String description,
        Long epicId,
        Long sprintId,
        Integer effort,
        List<TaskItemResource> tasks) {
}
