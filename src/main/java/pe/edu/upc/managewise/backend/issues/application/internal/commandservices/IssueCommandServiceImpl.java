package pe.edu.upc.managewise.backend.issues.application.internal.commandservices;
import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.issues.domain.model.aggregates.Issue;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateEventByIssueIdCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.CreateIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.DeleteIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.commands.UpdateIssueCommand;
import pe.edu.upc.managewise.backend.issues.domain.model.entities.EventRecordItem;
import pe.edu.upc.managewise.backend.issues.domain.services.IssueCommandService;
import pe.edu.upc.managewise.backend.issues.infrastructure.persistence.jpa.issues.IssueRepository;


import java.util.Optional;

@Service
public class IssueCommandServiceImpl implements IssueCommandService {
    //Dependency Injection
    private final IssueRepository issueRepository;
    public IssueCommandServiceImpl(IssueRepository issueRepository){
        this.issueRepository = issueRepository;
    }

    //CreateIssueCommand
    @Override
    public Long handle(CreateIssueCommand command) {
        //Constrains
        //Condition so that there is no same Issue title in the same Sprint
        var title = command.title();
        var sprintAssociate = command.sprintAssociate();
        if (this.issueRepository.existsByTitleAndSprintAssociate(title,sprintAssociate)) {
            throw new IllegalArgumentException("Issue with title " + title + " on Sprint "+ sprintAssociate + " already exists");
        }
        //Save and Try-catch
        var issue = new Issue(command);
        try {
            this.issueRepository.save(issue);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving issue: " + e.getMessage());
        }
        return issue.getId();
    }

    //UpdateIssueCommand
    @Override
    public Optional<Issue> handle(UpdateIssueCommand command) {
        //Constrains
        var issueId = command.issueId();
        var title = command.title();

        if (this.issueRepository.existsByTitleAndIdIsNot(title, issueId)) {
            throw new IllegalArgumentException("Issue with title " + title + " already exists");
        }

        // If the issue does not exist, throw an exception
        if (!this.issueRepository.existsById(issueId)) {
            throw new IllegalArgumentException("Issue with id " + issueId + " does not exist");
        }

        //update issue from Command
        var issueToUpdate = this.issueRepository.findById(issueId).get();
        issueToUpdate.updateInformation(command.title(),command.sprintAssociate() , command.description(), command.status(), command.priority(), command.assignedTo(), command.madeBy(), command.createdIn(), command.resolutionDate());

        try {
            var updatedProfile = this.issueRepository.save(issueToUpdate);
            return Optional.of(updatedProfile);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating issue: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteIssueCommand command) {
        // If the profile does not exist, throw an exception
        //condition if the id value exists or not to delete it
        if (!this.issueRepository.existsById(command.issueId())) {
            throw new IllegalArgumentException("Issue with id " + command.issueId() + " does not exist");
        }
        //Save and Try-catch
        // Try to delete the profile, if an error occurs, throw an exception
        try {
            this.issueRepository.deleteById(command.issueId());
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting issue: " + e.getMessage());
        }
    }

    @Override
    public Long handle(CreateEventByIssueIdCommand command) {
        var issueOptional = issueRepository.findById(command.issueId());
        if (issueOptional.isEmpty()) {
            throw new IllegalArgumentException("Issue with id " + command.issueId() + " does not exist");
        }

        var issue = issueOptional.get();
        var eventRecordItem = new EventRecordItem(issue, command.createdDate(), command.madeBy(), command.eventName(), command.description());
        issue.getEventRecord().getEventRecordItems().add(eventRecordItem);

        try {
            issueRepository.save(issue);
            return eventRecordItem.getId();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving event: " + e.getMessage());
        }
    }
}
