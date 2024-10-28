package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.Task;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.UserStory;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class TaskList {

    @OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public TaskList(){
        tasks = new ArrayList<>();
    }

    public void addTask(UserStory userStory, String title, String description){
        tasks.add(new Task(userStory, title, description));
    }

    public void removeTask(Long taskId){
        tasks.removeIf(task -> task.getId().equals(taskId));
    }

    public void updateTask(Long taskId, String title, String description){
        tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .ifPresent(task -> task.update(title, description));
    }

    /*parte para queries?*/
    public Task getTask(Long taskId){
        return tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .orElse(null);
    }

}
