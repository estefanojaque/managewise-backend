package pe.edu.upc.managewise.backend.backlog.domain.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
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
    @JoinColumn(name = "user_story")
    private UserStory userStory;

    private Status status;

    private String title;

    private String description;

    /*@NotNull
    private Long userStoryId;
    */

    public Task(UserStory userStory, String title, String description){
        this.title = title;
        this.description = description;
        this.userStory = userStory;
        this.status = Status.TO_DO;
    }

    public Task(){
    }

    public void updateStatus(Status status){
        this.status = status;
    }

}
