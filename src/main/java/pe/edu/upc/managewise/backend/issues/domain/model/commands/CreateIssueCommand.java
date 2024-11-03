package pe.edu.upc.managewise.backend.issues.domain.model.commands;

import pe.edu.upc.managewise.backend.issues.interfaces.rest.resources.EventRecordItemResource;

import java.util.List;

public record CreateIssueCommand(String title, int sprintAssociate , String description, String status, String priority, String assignedTo, String madeBy, String createdIn, String resolutionDate, List<EventRecordItemResource> history) {
}
