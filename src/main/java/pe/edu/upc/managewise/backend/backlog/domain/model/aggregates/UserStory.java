package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.*;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class UserStory extends AuditableAbstractAggregateRoot<UserStory> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Status status;

    /*
    @ManyToOne
    private Epic epic;
    */
    private Long epicId;

    /*
    @ManyToOne
    private SprintBacklog sprintBacklog;
    */
    private Long sprintBacklogId;

    //cambiar Integer a storypoints
    private Integer effort;

    @Embedded
    private final TaskList taskList;

    public UserStory() {
        this.taskList = new TaskList();
    }

    public UserStory(String title, String description, Long epicId, Long sprintBacklogId, Integer effort) {
        this.title = title;
        this.description = description;
        this.status = Status.TO_DO;
        this.epicId = epicId;
        this.sprintBacklogId = sprintBacklogId;
        this.effort = effort;
        this.taskList = new TaskList();
    }

    public UserStory(CreateUserStoryCommand command){
        this();
        this.title = command.title();
        this.description = command.description();
        this.status = Status.TO_DO;
        this.epicId = command.epicId();
        this.sprintBacklogId = command.sprintBacklogId();
        this.effort = command.effort();
    }

    /*de momento solo se puede actualizar el titulo y descripcion*/
    public UserStory updateInformation(String title, String description){
        this.title = title;
        this.description = description;
        return this;
    }

}
