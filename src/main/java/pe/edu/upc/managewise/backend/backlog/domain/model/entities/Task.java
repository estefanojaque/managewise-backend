package pe.edu.upc.managewise.backend.backlog.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Description;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Status;
import pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects.Title;
import pe.edu.upc.managewise.backend.shared.domain.model.entities.AuditableModel;

@Getter
@Entity
public class Task extends AuditableModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_story_id")
    @NotNull
    private UserStory userStory;

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

    public Task(UserStory userStory, String title, String description) {
        this.userStory = userStory;
        this.title = new Title(title);
        this.description = new Description(description);
        this.status = Status.TO_DO;
    }

    public Task() {
    }

    public void update(String title, String description) {
        this.title = new Title(title);
        this.description = new Description(description);
    }

    public void changeStatus(Status status) {
        this.status = status;
    }
}
