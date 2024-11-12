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
        Member member = new Member(command.personName(), command.role(), command.email(), command.streetAddress());
        memberRepository.save(member);
        return member.getId();
    }

    @Override
    public Optional<Member> handle(UpdateMemberCommand command) {
        Member member = memberRepository.findById(command.memberId())
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + command.memberId()));

        // Asegúrate de que los métodos de actualización estén correctamente implementados
        member.updateName(command.personName()); // Actualiza el nombre
        member.updateEmail(command.email());     // Actualiza el correo
        member.updateAddress(command.streetAddress());  // Actualiza la dirección

        memberRepository.save(member);
        return Optional.of(member);
    }

    @Override
    public List<Member> getAllMembers() {
        return memberRepository.findAll(); // Obtener todos los miembros
    }

    @Override
    public Optional<Member> fetchMemberById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return member;
        } else {
            System.out.println("Member not found with ID: " + id); // Imprime el mensaje en la consola
            return Optional.empty();
        }
    }

    @Override
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member not found with id: " + id));
    }

    @Override
    public void handle(DeleteMemberCommand command) {
        // Verificar si el miembro existe antes de eliminarlo
        if (!memberRepository.existsById(command.memberId())) {
            throw new MemberNotFoundException("Member not found with id: " + command.memberId());
        }
        memberRepository.deleteById(command.memberId());
    }
}
