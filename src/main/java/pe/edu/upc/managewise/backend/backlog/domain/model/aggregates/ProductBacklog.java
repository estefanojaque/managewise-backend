package pe.edu.upc.managewise.backend.backlog.domain.model.aggregates;

import jakarta.persistence.*;
import pe.edu.upc.managewise.backend.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Table(name = "product_backlog")
public class ProductBacklog extends AuditableAbstractAggregateRoot<ProductBacklog> {


}
