package pe.edu.upc.managewise.backend.members.domain.model.valueobjects;

public record EmailAddress(String email) {

    // Constructor with validation for the email format
    public EmailAddress {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        // Basic validation for email format (you can improve this with regex if needed)
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
