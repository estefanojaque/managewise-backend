package pe.edu.upc.managewise.backend.members.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.PersonName;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.ScrumRoles;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.StreetAddress;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "members")
@NoArgsConstructor // Constructor vacío requerido por JPA
@AllArgsConstructor // Constructor completo (opcional, si lo necesitas)
public class Member extends AuditableAbstractAggregateRoot<Member> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Embedded
    private PersonName personName;

    @Getter
    @Enumerated(EnumType.STRING) // Mapeo del enum
    @Column(name = "role")
    private ScrumRoles role;

    @Getter
    @Embedded
    private EmailAddress email;

    @Getter
    @Embedded
    private StreetAddress address;

    public Member(PersonName personName, ScrumRoles role, EmailAddress email, StreetAddress address) {
        if (personName == null || role == null || email == null || address == null) {
            throw new IllegalArgumentException("Los campos no pueden ser nulos");
        }
        this.personName = personName;
        this.role = role;
        this.email = email;
        this.address = address;
    }

    public void updateRole(ScrumRoles role) {
        this.role = role;
    }

    public void updateName(PersonName name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.personName = name;
    }

    public void updateEmail(EmailAddress email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = email;
    }

    public void updateAddress(StreetAddress address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        this.address = address;
    }

    public Long getId() {
        return id;
    }
}
