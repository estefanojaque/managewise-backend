package pe.edu.upc.managewise.backend.backlog.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Task;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateTaskCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.DeleteTaskCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateTaskCommand;
import pe.edu.upc.managewise.backend.backlog.domain.services.TaskCommandService;
import pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories.TaskRepository;

import java.util.Optional;

@Service
public class TaskCommandServiceImpl implements TaskCommandService {
    private final TaskRepository taskRepository;

    public TaskCommandServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Long handle(CreateTaskCommand command){
        var title = command.title();
        if(this.taskRepository.existsByTitle(title)){
            throw new IllegalArgumentException("Task with " + title + " as title already exists");
        }
        var task = new Task(command);
        try{
            this.taskRepository.save(task);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while saving task: " + e.getMessage());
        }
        return task.getId();
    }

    @Override
    public Optional<Task> handle(UpdateTaskCommand command){
        var taskId = command.id();
        var title = command.title();
        if(this.taskRepository.existsByTitleAndIdIsNot(title, taskId)){
            throw new IllegalArgumentException("Task with " + title + " as title already exists");
        }

        //si el profile no existe:
        if (!this.taskRepository.existsById(taskId)){
            throw new IllegalArgumentException("Task does not exist");
        }

        var taskToUpdate = this.taskRepository.findById(taskId).get();
        taskToUpdate.updateInformation(command.title(), command.description());

        try{
            var updatedTask = this.taskRepository.save(taskToUpdate);
            return Optional.of(updatedTask);
        } catch (Exception e){
            throw new IllegalArgumentException("Error while updating task: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteTaskCommand command){
        if(!this.taskRepository.existsById(command.taskId())){
            throw new IllegalArgumentException("Task does not exist");
        }

        try{
            this.taskRepository.deleteById(command.taskId());
        } catch(Exception e){
            throw new IllegalArgumentException("Error while deleting task: " + e.getMessage());
        }
    }
}
