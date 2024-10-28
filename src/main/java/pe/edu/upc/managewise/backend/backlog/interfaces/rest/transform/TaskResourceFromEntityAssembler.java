package pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform;

import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Task;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.TaskResource;

public class TaskResourceFromEntityAssembler {
    public static TaskResource toResourceFromEntity(Task entity){
        return new TaskResource(entity.getId(), entity.getTitle(), entity.getDescription());
    }
}
