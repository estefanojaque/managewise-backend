package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.UpdateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.SprintResource;

public class UpdateSprintCommandFromResourceAssembler {
    public static UpdateSprintCommand toCommandFromResource(Long id, SprintResource resource){
        return new UpdateSprintCommand(id, resource.title(), resource.goal());
    }
}
