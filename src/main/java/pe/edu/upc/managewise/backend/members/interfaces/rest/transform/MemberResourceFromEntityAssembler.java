package pe.edu.upc.managewise.backend.members.interfaces.rest.transform;

import org.springframework.stereotype.Component;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.MemberResource;

@Component
public class MemberResourceFromEntityAssembler {

    public static MemberResource toResource(Member member) {
        return new MemberResource(
                member.getId(), // Agrega el ID aquí
                member.getPersonName(),
                member.getEmail(),
                member.getAddress(),
                member.getRole()
        );
    }
}
