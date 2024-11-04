package pe.edu.upc.managewise.backend.members.domain.model.commands;

import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.PersonName;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.StreetAddress;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;

public record CreateMemberCommand(
        Long memberId,
        PersonName personName,
        EmailAddress email,
        StreetAddress address,
        ScrumRoles role
) {}
