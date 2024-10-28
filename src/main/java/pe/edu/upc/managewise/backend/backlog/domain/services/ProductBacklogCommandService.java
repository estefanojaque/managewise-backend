package pe.edu.upc.managewise.backend.backlog.domain.services;

import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateEpicCommand;

public interface ProductBacklogCommandService {
    Long handle(CreateEpicCommand command);

}
