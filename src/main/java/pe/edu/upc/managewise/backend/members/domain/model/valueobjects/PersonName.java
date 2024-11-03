package pe.edu.upc.managewise.backend.members.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PersonName(String firstName, String lastName) {

    // Método para obtener el nombre completo
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
