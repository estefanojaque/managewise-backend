package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.UserStory;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.UserStoryResource;

public class UserStoryResourceFromEntityAssembler {
    public static UserStoryResource toResourceFromEntity(UserStory entity){
        return new UserStoryResource(entity.getId(), entity.getTitle(), entity.getDescription());
    }
}
