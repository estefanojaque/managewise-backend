package pe.edu.upc.managewise.backend.backlog.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.managewise.backend.backlog.domain.model.aggregates.Task;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdIsNot(String title, Long id);
    Optional<Task> findByTitle(String title);
}
