package pe.edu.upc.managewise.backend.backlog.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.AddTaskITemToUserStoryTaskListCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetTaskItemByUserStoryIdAndTaskIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.UserStoryCommandService;
import pe.edu.upc.managewise.backend.backlog.domain.services.UserStoryQueryService;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.TaskItemResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.TaskItemResourceFromEntityAssembler;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/user-stories/{id}/task-items", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User stories")
public class UserStoryTaskListController {
    private final UserStoryCommandService userStoryCommandService;
    private final UserStoryQueryService userStoryQueryService;

    public UserStoryTaskListController(UserStoryCommandService userStoryCommandService, UserStoryQueryService userStoryQueryService) {
        this.userStoryCommandService = userStoryCommandService;
        this.userStoryQueryService = userStoryQueryService;
    }

    @PostMapping("{taskId}")
    public ResponseEntity<TaskItemResource> addTaskToUserStoryTaskList(
            @PathVariable Long taskId, String title, String description, Integer estimation,
            Long userId) {
        userStoryCommandService.handle(new AddTaskITemToUserStoryTaskListCommand(title, description, estimation, userId));
        var getTaskItemByTaskIdQuery = new GetTaskItemByUserStoryIdAndTaskIdQuery(taskId, userId);
        var taskItem = userStoryQueryService.handle(getTaskItemByTaskIdQuery);

        if (taskItem.isEmpty())
            return ResponseEntity.notFound().build();
        else {
            var taskItemResource = TaskItemResourceFromEntityAssembler.toResourceFromEntity(taskItem.get());
            return ResponseEntity.ok(taskItemResource);
        }
    }
}
