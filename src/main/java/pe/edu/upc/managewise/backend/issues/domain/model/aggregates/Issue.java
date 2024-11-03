package pe.edu.upc.managewise.backend.issues.domain.model.aggregates;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.valueobjects.EventRecord;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "issues")
public class Issue extends AuditableAbstractAggregateRoot<Issue> {
    @Getter
    @NotNull
    @NotBlank
    @Column(name = "title", length = 70, nullable = false)
    private String title;

    @Getter
    @Min(0)
    @Max(6)
    @Column(name = "sprint_associate", columnDefinition = "smallint", nullable = false)
    private int sprintAssociate;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "description", length = 300, nullable = false)
    private String description;


    @Getter
    @NotNull
    @NotBlank
    @Column(name = "status", length = 20, nullable = false)
    private String status;

    //private IssueStatus status;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "priority", length = 10, nullable = false)
    private String priority;

    //private IssuePriority priority;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "assigned_to", length = 70, nullable = false)
    private String assignedTo;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "made_by", length = 70, nullable = false)
    private String madeBy;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "created_in", length = 70, nullable = false)
    private String createdIn;

    @Getter
    @NotNull
    @NotBlank
    @Column(name = "resolution_date", length = 70, nullable = false)
    private String resolutionDate;

    @Embedded
    private EventRecord eventRecord;

    public Issue() {
        this.eventRecord = new EventRecord();
    }

    public Issue(String title, int sprintAssociate, String description, String status,String priority, String assignedTo, String madeBy, String createdIn, String resolutionDate) {
        this.title = title;
        this.sprintAssociate = sprintAssociate;
        this.description = description;
        //this.status = IssueStatus.TO_DO;
        this.status = status;
        this.priority = priority;
        //this.priority = IssuePriority.MEDIUM;
        this.assignedTo = assignedTo;
        this.madeBy = madeBy;
        this.createdIn = createdIn;
        this.resolutionDate = resolutionDate;
        this.eventRecord = new EventRecord();
    }

    /*
    public void to_do() {
        this.status = IssueStatus.TO_DO;
    }

    public void in_progress() {
        this.status = IssueStatus.IN_PROGRESS;
    }

    public void done() {
        this.status = IssueStatus.DONE;
    }

    public String getStatus() {
        return this.status.name().toLowerCase();
    }
    */
    //---------------------------------------------------
  public Issue(CreateIssueCommand command) {
    this.title = command.title();
    this.sprintAssociate = command.sprintAssociate();
    this.description = command.description();
    this.status = command.status();
    this.priority= command.priority();
    this.assignedTo = command.assignedTo();
    this.madeBy = command.madeBy();
    this.createdIn = command.createdIn();
    this.resolutionDate = command.resolutionDate();
    this.eventRecord = new EventRecord();
  }

  public Issue updateInformation(String title, int sprintAssociate , String description, String status, String priority, String assignedTo, String madeBy, String createdIn, String resolutionDate) {
    this.title = title;
    this.sprintAssociate = sprintAssociate;
    this.description = description;
    this.status = status;
    this.priority= priority;
    this.assignedTo = assignedTo;
    this.madeBy = madeBy;
    this.createdIn = createdIn;
    this.resolutionDate = resolutionDate;
    return this;
  }

    public EventRecord getEventRecord() {
        return eventRecord;
    }
}