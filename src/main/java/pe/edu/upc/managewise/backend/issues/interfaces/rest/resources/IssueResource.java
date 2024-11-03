package pe.edu.upc.managewise.backend.issues.interfaces.rest.resources;

import java.util.List;

//This is my output JSON
public record IssueResource(Long id, String title, int sprintAssociate , String description, String status, String priority, String assignedTo, String madeBy, String createdIn, String resolutionDate, List<EventRecordItemResource> history) {
}
