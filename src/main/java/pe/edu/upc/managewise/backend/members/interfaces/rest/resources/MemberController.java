package pe.edu.upc.managewise.backend.members.interfaces.rest.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import pe.edu.upc.managewise.backend.members.domain.exceptions.MemberNotFoundException;
import pe.edu.upc.managewise.backend.members.domain.model.commands.CreateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.commands.DeleteMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.UpdateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.services.MemberCommandService;
import pe.edu.upc.managewise.backend.members.interfaces.rest.transform.CreateMemberCommandFromResourceAssembler;
import pe.edu.upc.managewise.backend.members.interfaces.rest.transform.MemberResourceFromEntityAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/members", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Members", description = "Member Management Endpoints")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberResourceFromEntityAssembler memberResourceAssembler;
    private final CreateMemberCommandFromResourceAssembler createMemberCommandAssembler;

    @Autowired
    public MemberController(MemberCommandService memberCommandService,
                            MemberResourceFromEntityAssembler memberResourceAssembler,
                            CreateMemberCommandFromResourceAssembler createMemberCommandAssembler) {
        this.memberCommandService = memberCommandService;
        this.memberResourceAssembler = memberResourceAssembler;
        this.createMemberCommandAssembler = createMemberCommandAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResource> getMemberById(@PathVariable Long id) {
        Member member = memberCommandService.getMemberById(id);
        if (member == null) {
            throw new MemberNotFoundException("Member not found with id: " + id);
        }
        MemberResource memberResource = memberResourceAssembler.toResource(member);
        return ResponseEntity.ok(memberResource);
    }

    @PostMapping
    public ResponseEntity<MemberResource> createMember(@RequestBody CreateMemberCommand command) {
        // Aquí puedes ignorar el memberId en la lógica, ya que el comando no lo incluye.
        Long memberId = memberCommandService.handle(command); // solo usa el resto de los campos.

        // Asegúrate de que el ID no sea nulo o cero antes de buscar el miembro
        if (memberId == null || memberId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Manejo de error
        }

        // Obtener el miembro recién creado por su ID
        Member createdMember = memberCommandService.getMemberById(memberId);

        // Convertir el miembro a MemberResource
        MemberResource memberResource = memberResourceAssembler.toResource(createdMember);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestBody UpdateMemberCommand command) {
        command = new UpdateMemberCommand(id, command.personName(), command.email(), command.address(), command.role());
        memberCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberCommandService.handle(new DeleteMemberCommand(id));
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFound(MemberNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
    @GetMapping
    public ResponseEntity<List<MemberResource>> getAllMembers() {
        List<Member> members = memberCommandService.getAllMembers(); // Usar la instancia del servicio
        List<MemberResource> memberResources = members.stream()
                .map(MemberResourceFromEntityAssembler::toResource) // Cambiado a memberResourceAssembler
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberResources);
    }
}
