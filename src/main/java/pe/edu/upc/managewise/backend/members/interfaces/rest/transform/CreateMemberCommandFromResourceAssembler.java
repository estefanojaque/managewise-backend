package pe.edu.upc.managewise.backend.members.interfaces.rest.transform;

import org.springframework.stereotype.Component;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.MemberResource;

@Component
public class CreateMemberCommandFromResourceAssembler {

    public CreateMemberCommand toCommand(MemberResource resource) {
        return new CreateMemberCommand(
                resource.getId(),
                resource.getPersonName(), // Asegúrate de que estos value objects se puedan usar directamente
                resource.getEmail(),
                resource.getAddress(),
                resource.getRole()
        );
    }
}
