package pe.edu.upc.managewise.backend.issues.interfaces.rest.resources;

import java.util.List;

public record CreateIssueResource(
        String title,
        String sprintAssociate,
        String description,
        String status,
        String priority,
        String assignedTo,
        String madeBy,
        String createdIn,
        String resolutionDate
) {}