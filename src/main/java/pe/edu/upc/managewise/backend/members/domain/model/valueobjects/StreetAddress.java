// 📁 pe/edu/upc/managewise/managewise_members/members/domain/model/valueobjects/StreetAddress.java
package pe.edu.upc.managewise.backend.members.domain.model.valueobjects;

public record StreetAddress(String street, String number, String city, String postalCode, String country) {
    // Método para obtener la dirección en formato completo
    public String getStreetAddress() {
        return street + " " + number + ", " + city + ", " + postalCode + ", " + country;
    }
}
