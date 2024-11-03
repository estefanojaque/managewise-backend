package pe.edu.upc.managewise.backend.issues.domain.model.commands;

public record UpdateIssueCommand(Long issueId, String title, int sprintAssociate , String description, String status, String priority, String assignedTo, String madeBy, String createdIn, String resolutionDate) {
}
