package pe.edu.upc.managewise.backend.backlog.domain.model.valueobjects;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import pe.edu.upc.managewise.backend.backlog.domain.model.entities.Epic;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class EpicList {
    @OneToMany(mappedBy = "productBacklog", cascade = CascadeType.ALL)
    private List<Epic> epics;

    public EpicList(){
        epics = new ArrayList<>();
    }
}
