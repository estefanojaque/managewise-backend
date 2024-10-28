package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.Task;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class TaskList {

    @OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public TaskList(){
        tasks = new ArrayList<>();
    }

}
