package pe.edu.upc.managewise.backend.members.domain.model.valueobjects;

public record StreetAddress(String street, String city, String country) {

    // Constructor with validation or custom logic
    public StreetAddress {
        // Custom logic or validation can go here
        if (street == null || street.isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }

        if (city == null || city.isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }

        if (country == null || country.isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }
    }

    public String getStreetAddress() {
        return street +  ", " + city +  ", " + country;
    }
}
