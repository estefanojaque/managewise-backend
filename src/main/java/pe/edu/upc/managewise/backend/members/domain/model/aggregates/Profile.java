// 📁 pe/edu/upc/managewise/managewise_members/members/domain/model/aggregates/Profile.java
package pe.edu.upc.managewise.backend.members.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.PersonName;
import pe.edu.upc.managewise.backend.members.domain.model.valueobjects.StreetAddress;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "profiles")
public class Profile extends AuditableAbstractAggregateRoot<Profile> {

    @Getter
    @Embedded
    private PersonName name;

    @Embedded
    private EmailAddress email;

    @Embedded
    private StreetAddress address;

    // Constructor sin parámetros (requerido por JPA)
    public Profile() {
        this.name = null;
        this.email = null;
        this.address = null;
    }

    public Profile(PersonName name, EmailAddress email, StreetAddress address) {
        if (name == null) {
            throw new IllegalArgumentException("PersonName cannot be null");
        }
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // Métodos de actualización para los atributos embebidos
    public void updateName(PersonName name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public void updateEmail(EmailAddress email) {
        this.email = email;
    }

    public void updateAddress(StreetAddress address) {
        this.address = address;
    }

    // Getters para cada value object
    public EmailAddress getEmail() {
        return email;
    }

    public StreetAddress getAddress() {
        return address;
    }
}
