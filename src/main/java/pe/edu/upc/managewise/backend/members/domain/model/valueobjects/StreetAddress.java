package pe.edu.upc.managewise.backend.members.domain.model.valueobjects;

public record StreetAddress(String streetAddress) {

    // Constructor with validation or custom logic
    public StreetAddress {
        // Custom logic or validation can go here
        if (streetAddress == null || streetAddress.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
    }

    public String getStreetAddress() {
        return streetAddress ;
    }
}
