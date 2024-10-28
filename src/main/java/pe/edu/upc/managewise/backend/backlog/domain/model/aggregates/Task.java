package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateTaskCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Description;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Title;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
public class Task extends AuditableAbstractAggregateRoot<Task> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Status status;

    /*create*/

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = Status.TO_DO;
    }

    public Task(){}

    public Task(CreateTaskCommand command){
        this();
        this.title = command.title();
        this.description = command.description();
    }

    /*update*/
    public Task updateInformation(String title, String description){
        this.title = title;
        this.description = description;
        return this;
    }

}
