package pe.edu.upc.managewise.backend.members.interfaces.rest.resources;

import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.PersonName;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.StreetAddress;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;

public class MemberResource {
    private final Long id; // ID debe ser final para seguir la convención de diseño

    private final PersonName personName;
    private final EmailAddress email;
    private final StreetAddress streetAddress;
    private final ScrumRoles role;

    public MemberResource(Long id, PersonName personName, EmailAddress email, StreetAddress streetAddress, ScrumRoles role) {
        this.id = id; // Asigna el ID en el constructor
        this.personName = personName;
        this.email = email;
        this.streetAddress = streetAddress;
        this.role = role;
    }

    // Getters
    public Long getId() {
        return id; // Método para obtener el ID
    }

    public PersonName getPersonName() {
        return personName;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public StreetAddress getStreetAddress() {
        return streetAddress;
    }

    public ScrumRoles getRole() {
        return role;
    }
}
