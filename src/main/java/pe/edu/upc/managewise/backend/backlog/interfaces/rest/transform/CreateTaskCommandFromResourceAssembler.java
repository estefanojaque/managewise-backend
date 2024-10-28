package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateTaskCommand;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateTaskResource;

public class CreateTaskCommandFromResourceAssembler {
    public static CreateTaskCommand toCommandFromResource(CreateTaskResource resource){
        return new CreateTaskCommand(resource.title(), resource.description());
    }
}
