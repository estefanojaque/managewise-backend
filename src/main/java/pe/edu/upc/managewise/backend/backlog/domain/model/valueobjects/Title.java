package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Title(String title) {
    public Title(){
        this(null);
    }
    public Title{
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
    }
}
