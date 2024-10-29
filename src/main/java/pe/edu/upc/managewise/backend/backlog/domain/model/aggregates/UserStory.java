package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.TaskList;
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

    @Embedded
    private final TaskList taskList;

    /*
    @ElementCollection
    private List<Long> taskIds = new ArrayList<>();
    */

    public UserStory(String title, String description) {
        this.title = title;
        this.description = description;
        this.taskList = new TaskList();
        this.status = Status.TO_DO;
    }

    public UserStory() {
        this.taskList = new TaskList();
    }

    /*create*/
    public UserStory(CreateUserStoryCommand command){
        this();
        this.title = command.title();
        this.description = command.description();
    }

    /*update*/
    public UserStory updateInformation(String title, String description){
        this.title = title;
        this.description = description;
        return this;
    }

    /*
    public void addTaskToUserStory(String title, String description){
        taskList.addToTaskList(this, title, description);
    }*/

}
