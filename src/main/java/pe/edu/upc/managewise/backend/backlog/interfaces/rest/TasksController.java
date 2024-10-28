package pe.edu.upc.managewise.backend.backlog.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteTaskCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateTaskCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetAllTasksQuery;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetTaskByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.TaskCommandService;
import pe.edu.upc.managewise.backend.backlog.domain.services.TaskQueryService;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateTaskResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.TaskResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.CreateTaskCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.TaskResourceFromEntityAssembler;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.UpdateTaskCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Tasks", description = "Task Management Endpoints")
public class TasksController {
    private final TaskQueryService taskQueryService;
    private final TaskCommandService taskCommandService;

    public TasksController(TaskQueryService taskQueryService, TaskCommandService taskCommandService) {
        this.taskQueryService = taskQueryService;
        this.taskCommandService = taskCommandService;
    }

    @PostMapping
    public ResponseEntity<TaskResource> createTask(@RequestBody CreateTaskResource resource) {
        var createTaskCommand = CreateTaskCommandFromResourceAssembler
                .toCommandFromResource(resource);
        var taskId = this.taskCommandService.handle(createTaskCommand);

        if (taskId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getTaskByIdQuery = new GetTaskByIdQuery(taskId);
        var optionalTask = this.taskQueryService.handle(getTaskByIdQuery);

        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(optionalTask.get());
        return new ResponseEntity<>(taskResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResource>> getAllTasks() {
        var getAllTasksQuery = new GetAllTasksQuery();
        var tasks = this.taskQueryService.handle(getAllTasksQuery);
        var taskResources = tasks.stream()
                .map(TaskResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(taskResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResource> getTaskById(@PathVariable Long id) {
        var getTaskByIdQuery = new GetTaskByIdQuery(id);
        var optionalTask = this.taskQueryService.handle(getTaskByIdQuery);
        if (optionalTask.isEmpty())
            return ResponseEntity.badRequest().build();
        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(optionalTask.get());
        return ResponseEntity.ok(taskResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResource> updateTask(@PathVariable Long id, @RequestBody TaskResource resource) {
        var updateTaskCommand = UpdateTaskCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalTask = this.taskCommandService.handle(updateTaskCommand);

        if (optionalTask.isEmpty())
            return ResponseEntity.badRequest().build();
        var taskResource = TaskResourceFromEntityAssembler.toResourceFromEntity(optionalTask.get());
        return ResponseEntity.ok(taskResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        var deleteTaskCommand = new DeleteTaskCommand(id);
        this.taskCommandService.handle(deleteTaskCommand);
        return ResponseEntity.noContent().build();
    }
}