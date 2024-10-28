package pe.edu.upc.managewise.backend.backlog.domain.model.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.SprintBacklog;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Description;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.TaskList;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Title;
import pe.edu.upc.managewise.backend.shared.domain.model.entities.AuditableModel;

@Getter
@Entity
public class UserStory extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "epic_id")
    @NotNull
    private Epic epic;

    @Embedded
    private TaskList taskList;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "title", nullable = false))
    })
    private Title title;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "description", column = @Column(name = "description", nullable = false))
    })
    private Description description;

    private Status status;

    public UserStory(Epic epic, String title, String description) {
        this.epic = epic;
        this.title = new Title(title);
        this.description = new Description(description);
        this.taskList = new TaskList();
        this.status = Status.TO_DO;
    }

    public UserStory() {
    }

    public void update(String title, String description) {
        this.title = new Title(title);
        this.description = new Description(description);
    }

    public void addTask(String title, String description){
        taskList.addTask(this, title, description);
    }

    public void removeTask(Long taskId){
        taskList.removeTask(taskId);
    }

    public void updateTask(Long taskId, String title, String description){
        taskList.updateTask(taskId, title, description);
    }

    public Task getTask(Long taskId){
        return taskList.getTask(taskId);
    }
}
