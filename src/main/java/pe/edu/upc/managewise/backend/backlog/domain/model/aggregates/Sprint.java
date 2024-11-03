package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.SprintStatus;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Getter
@Entity
public class Sprint extends AuditableAbstractAggregateRoot<Sprint> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String goal;
    private SprintStatus status;

    public Sprint(String title, String goal) {
        this.title = title;
        this.goal = goal;
        this.status = SprintStatus.STARTED;
    }

    public Sprint() {
    }

    public Sprint(CreateSprintCommand command){
        this();
        this.title = command.title();
        this.goal = command.goal();
        this.status = SprintStatus.STARTED;
    }

    public Sprint updateInformation(String title, String goal){
        this.title = title;
        this.goal = goal;
        return this;
    }

}
