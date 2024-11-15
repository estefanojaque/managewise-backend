package pe.edu.upc.managewise.backend.members.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.PersonName;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.StreetAddress;


public record CreateMemberResource(
        PersonName personName,
        EmailAddress email,
        StreetAddress streetAddress,
        ScrumRoles role
) {}
