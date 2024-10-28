package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateEpicCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Epic extends AuditableAbstractAggregateRoot<Epic> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Status status;

    @ElementCollection
    private List<Long> userStoryIds = new ArrayList<>();

    public Epic(String title, String description) {
        this.title = title;
        this.description = description;
        this.status = Status.TO_DO;
    }

    public Epic() {
    }

    /*create*/

    public Epic(CreateEpicCommand command){
        this();
        this.title = command.title();
        this.description = command.description();
    }

    /*update*/

    public Epic updateInformation(String title, String description){
        this.title = title;
        this.description = description;
        return this;
    }

}
