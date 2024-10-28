package pe.edu.upc.managewise.backend.backlog.domain.model.entities;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.ProductBacklog;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Description;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Title;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.UserStoryList;
import pe.edu.upc.managewise.backend.shared.domain.model.entities.AuditableModel;

@Getter
@Entity
public class Epic extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*relacion a product backlog*/
    @ManyToOne
    private ProductBacklog productBacklog;

    @Embedded
    private UserStoryList userStoryList;

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

    public Epic(String title, String description) {
        this.title = new Title(title);
        this.description = new Description(description);
        this.userStoryList = new UserStoryList();
        this.status = Status.TO_DO;
    }

    public Epic() {
    }
}
