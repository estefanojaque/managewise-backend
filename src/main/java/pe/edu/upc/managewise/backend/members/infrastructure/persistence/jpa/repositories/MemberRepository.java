// 📁 pe/edu/upc/managewise/managewise_members/members/infrastructure/persistence/jpa/repositories/MemberRepository.java
package pe.edu.upc.managewise.backend.members.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

     Optional<Member> findByPersonName_FirstNameAndPersonName_LastName(String firstName, String lastName);

}
