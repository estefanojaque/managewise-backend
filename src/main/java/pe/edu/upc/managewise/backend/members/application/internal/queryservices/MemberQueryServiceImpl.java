package pe.edu.upc.managewise.backend.members.application.internal.queryservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.services.MemberQueryService;
import pe.edu.upc.managewise.backend.members.infrastructure.persistence.jpa.repositories.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberQueryServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Optional<Member> findById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Override
    public Member saveMember(Member member) {
        return memberRepository.save(member); // Supone que el repositorio tiene este método
    }

}