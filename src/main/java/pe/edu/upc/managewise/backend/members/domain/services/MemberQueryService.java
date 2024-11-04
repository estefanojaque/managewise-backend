package pe.edu.upc.managewise.backend.members.domain.services;

import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;

import java.util.List;
import java.util.Optional;

public interface MemberQueryService {
    Optional<Member> findById(Long memberId);
    List<Member> findAll();
    Member saveMember(Member member);

}