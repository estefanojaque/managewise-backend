package pe.edu.upc.managewise.backend.issues.interfaces.rest.resources;

import java.util.List;

public record CreateIssueResource(
        String title,
        int sprintAssociate,
        String description,
        String status,
        String priority,
        String assignedTo,
        String madeBy,
        String createdIn,
        String resolutionDate
) {}