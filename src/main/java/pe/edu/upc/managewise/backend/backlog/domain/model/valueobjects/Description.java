package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Description(String description) {
    public Description(){
        this(null);
    }

    public Description{
        if (description == null || description.isBlank()){
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
    }
}
