package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateTaskCommand;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.TaskResource;

public class UpdateTaskCommandFromResourceAssembler {
    public static UpdateTaskCommand toCommandFromResource(Long id, TaskResource resource){
        return new UpdateTaskCommand(id, resource.title(), resource.description());
    }
}
