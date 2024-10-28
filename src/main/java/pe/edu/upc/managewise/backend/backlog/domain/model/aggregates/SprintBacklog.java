package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "sprint_backlog")
public class SprintBacklog extends AuditableAbstractAggregateRoot<SprintBacklog>{
}
