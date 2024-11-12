package pe.edu.upc.managewise.backend.members.interfaces.rest.transform;

import org.springframework.stereotype.Component;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.CreateMemberResource;

@Component
public class CreateMemberCommandFromResourceAssembler {

    public CreateMemberCommand toCommand(CreateMemberResource resource) {
        return new CreateMemberCommand(
                null, // o 0L si se necesita un valor numérico
                resource.personName(),
                resource.email(),
                resource.streetAddress(),
                resource.role()
        );
    }
}
