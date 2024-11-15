package pe.edu.upc.managewise.backend.members.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PersonName(String fullName) {

    // Constructor with validation for first and last names
    public PersonName {
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

    }

    // Método para obtener el nombre completo
    public String getFullName() {
        return fullName;}
}
