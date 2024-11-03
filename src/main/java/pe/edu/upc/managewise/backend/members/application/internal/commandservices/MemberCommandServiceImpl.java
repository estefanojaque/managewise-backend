package pe.edu.upc.managewise.backend.members.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.managewise.backend.members.domain.exceptions.MemberNotFoundException;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.UpdateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.DeleteMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.services.MemberCommandService;
import pe.edu.upc.managewise.backend.members.infrastructure.persistence.jpa.repositories.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberCommandServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Long handle(CreateMemberCommand command) {
        Member member = new Member(command.personName(), command.role(), command.email(), command.address());
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public Optional<Member> handle(UpdateMemberCommand command) {
        Member member = memberRepository.findById(command.memberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // Asegúrate de que estos métodos existen en la clase Member
        member.updateName(command.personName()); // Método que debes implementar en Member
        member.updateEmail(command.email());     // Método que debes implementar en Member
        member.updateAddress(command.address());  // Método que debes implementar en Member

        memberRepository.save(member);
        return Optional.of(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // Suponiendo que tienes un método findAll en tu repositorio
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));
    }


    @Override
    public void handle(DeleteMemberCommand command) {
        memberRepository.deleteById(command.memberId());
    }
}
