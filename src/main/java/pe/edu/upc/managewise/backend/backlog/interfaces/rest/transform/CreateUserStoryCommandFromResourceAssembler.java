package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateUserStoryCommand;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.CreateUserStoryResource;

public class CreateUserStoryCommandFromResourceAssembler {
    public static CreateUserStoryCommand toCommandFromResource(CreateUserStoryResource resource){
        return new CreateUserStoryCommand(resource.title(), resource.description(),
                resource.epicId(), resource.sprintId(), resource.effort());
    }
}
