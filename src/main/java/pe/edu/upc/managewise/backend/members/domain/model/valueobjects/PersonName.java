package pe.edu.upc.managewise.backend.members.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PersonName(String firstName, String lastName) {

    // Constructor with validation for first and last names
    public PersonName {
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
    }

    // Método para obtener el nombre completo
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
