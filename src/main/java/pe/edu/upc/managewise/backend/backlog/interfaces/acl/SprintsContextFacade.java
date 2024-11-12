package pe.edu.upc.managewise.backend.backlog.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Sprint;
import pe.edu.upc.managewise.backend.backlog.domain.model.commands.CreateSprintCommand;
import pe.edu.upc.managewise.backend.backlog.domain.model.queries.GetSprintByIdQuery;
import pe.edu.upc.managewise.backend.backlog.domain.services.SprintCommandService;
import pe.edu.upc.managewise.backend.backlog.domain.services.SprintQueryService;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.resources.SprintResource;
import pe.edu.upc.managewise.backend.backlog.interfaces.rest.transform.SprintResourceFromEntityAssembler;

import java.util.Optional;

@Service
public class SprintsContextFacade {
    private final SprintCommandService sprintCommandService;
    private final SprintQueryService sprintQueryService;

    public SprintsContextFacade(SprintCommandService sprintCommandService, SprintQueryService sprintQueryService) {
        this.sprintCommandService = sprintCommandService;
        this.sprintQueryService = sprintQueryService;
    }

    public Optional<SprintResource> fetchSprintById(Long sprintId){
        var getSPrintByIdQuery = new GetSprintByIdQuery(sprintId);
        var optionalSprint = sprintQueryService.handle(getSPrintByIdQuery);
        if(optionalSprint.isEmpty()){
            return Optional.empty();
        }
        var sprintResource = SprintResourceFromEntityAssembler.toResourceFromEntity(optionalSprint.get());
        return Optional.of(sprintResource);
    }

}
