package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.*;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.Task;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class TaskList {
    //@OneToMany(mappedBy = "userStoryId", cascade = CascadeType.ALL)
    @OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public TaskList(){
        tasks = new ArrayList<>();
    }

    public void addToTaskList(UserStory _userStory, String title, String description){
        if (_userStory == null) {
            return;
            //throw new IllegalArgumentException("User story cannot be null");
        }
        Long userStoryId = _userStory.getId();
        Task task = new Task(_userStory, title, description);
        tasks.add(task);
    }

    public void updateTaskStatus(Long taskId, Status status){
        tasks.stream()
                .filter(task -> task.getId().equals(taskId))
                .findFirst()
                .ifPresent(task -> task.updateStatus(status));
    }


}
