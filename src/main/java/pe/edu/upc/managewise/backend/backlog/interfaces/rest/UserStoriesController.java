package pe.edu.upc.managewise.backend.backlog.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllUserStoriesQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetUserStoryByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.UserStoryCommandService;
import pe.edu.upc.managewise.backend.backlog.domain.services.UserStoryQueryService;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateUserStoryResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.TaskItemResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.UserStoryResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.CreateUserStoryCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.TaskItemResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.UserStoryResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.UpdateUserStoryCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/user-stories", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User Stories", description = "User Story Management Endpoints")
public class UserStoriesController {
    private final UserStoryQueryService userStoryQueryService;
    private final UserStoryCommandService userStoryCommandService;

    public UserStoriesController(UserStoryQueryService userStoryQueryService, UserStoryCommandService userStoryCommandService) {
        this.userStoryQueryService = userStoryQueryService;
        this.userStoryCommandService = userStoryCommandService;
    }

    @PostMapping
    public ResponseEntity<UserStoryResource> createUserStory(@RequestBody CreateUserStoryResource resource) {
        var createUserStoryCommand = CreateUserStoryCommandFromResourceAssembler.toCommandFromResource(resource);
        var userStoryId = this.userStoryCommandService.handle(createUserStoryCommand);

        if (userStoryId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getUserStoryByIdQuery = new GetUserStoryByIdQuery(userStoryId);
        var optionalUserStory = this.userStoryQueryService.handle(getUserStoryByIdQuery);

        var userStoryResource = UserStoryResourceFromEntityAssembler.toResourceFromEntity(optionalUserStory.get());
        return new ResponseEntity<>(userStoryResource, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<TaskItemResource> createTask(@PathVariable Long id, @RequestBody CreateTaskItemResource resource) {
        var createTaskCommand = new CreateTaskITemByUserStoryIdCommand(id, resource.title(), resource.description(), resource.estimation());
        var taskId = this.userStoryCommandService.handle(createTaskCommand);


        if (taskId == null || taskId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getUserStoryByIdQuery = new GetUserStoryByIdQuery(id);
        var optionalUserStory = this.userStoryQueryService.handle(getUserStoryByIdQuery);


        if (optionalUserStory.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var createdTask = optionalUserStory.get().getTaskList().getTasks().stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst().orElse(null);


        if (createdTask == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        var taskResource = TaskItemResourceFromEntityAssembler.toResourceFromEntity(createdTask);
        return new ResponseEntity<>(taskResource, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/tasks/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id, @PathVariable Long taskId) {
        var deleteTaskCommand = new DeleteTaskCommand(id, taskId);
        this.userStoryCommandService.handle(deleteTaskCommand);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskItemResource>> getAllTasksbyUserStoryId(@PathVariable Long id) {
        var getUserStoryByIdQuery = new GetUserStoryByIdQuery(id);
        var optionalUserStory = this.userStoryQueryService.handle(getUserStoryByIdQuery);

        if (optionalUserStory.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var taskResources = optionalUserStory.get().getTaskList().getTasks().stream()
                .map(task -> new TaskItemResource(task.getId(), task.getTitle(), task.getDescription(), task.getEstimation()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(taskResources);
    }



    @GetMapping
    public ResponseEntity<List<UserStoryResource>> getAllUserStories() {
        var getAllUserStoriesQuery = new GetAllUserStoriesQuery();
        var userStories = this.userStoryQueryService.handle(getAllUserStoriesQuery);
        var userStoryResources = userStories.stream()
                .map(UserStoryResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userStoryResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserStoryResource> getUserStoryById(@PathVariable Long id) {
        var getUserStoryByIdQuery = new GetUserStoryByIdQuery(id);
        var optionalUserStory = this.userStoryQueryService.handle(getUserStoryByIdQuery);
        if (optionalUserStory.isEmpty())
            return ResponseEntity.badRequest().build();
        var userStoryResource = UserStoryResourceFromEntityAssembler.toResourceFromEntity(optionalUserStory.get());
        return ResponseEntity.ok(userStoryResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserStoryResource> updateUserStory(@PathVariable Long id, @RequestBody UserStoryResource resource) {
        var updateUserStoryCommand = UpdateUserStoryCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalUserStory = this.userStoryCommandService.handle(updateUserStoryCommand);

        if (optionalUserStory.isEmpty())
            return ResponseEntity.badRequest().build();
        var userStoryResource = UserStoryResourceFromEntityAssembler.toResourceFromEntity(optionalUserStory.get());
        return ResponseEntity.ok(userStoryResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserStory(@PathVariable Long id) {
        var deleteUserStoryCommand = new DeleteUserStoryCommand(id);
        this.userStoryCommandService.handle(deleteUserStoryCommand);
        return ResponseEntity.noContent().build();
    }
}
