package pe.edu.upc.managewise.backend.members.interfaces.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import pe.edu.upc.managewise.backend.members.domain.exceptions.MemberNotFoundException;
import pe.edu.upc.managewise.backend.members.domain.model.aggregates.Member;
import pe.edu.upc.managewise.backend.members.domain.model.commands.DeleteMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.model.commands.UpdateMemberCommand;
import pe.edu.upc.managewise.backend.members.domain.services.MemberCommandService;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.CreateMemberResource;
import pe.edu.upc.managewise.backend.members.interfaces.rest.resources.MemberResource;
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
    public ResponseEntity<MemberResource> createMember(@RequestBody CreateMemberResource resource) {
        // Convierte el recurso en un comando usando el ensamblador
        var command = createMemberCommandAssembler.toCommand(resource);
        Long memberId = memberCommandService.handle(command);

        // Verifica que el ID no sea nulo o cero antes de buscar el miembro creado
        if (memberId == null || memberId == 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Manejo de error en caso de falla
        }

        // Obtener el miembro recién creado y convertirlo a MemberResource
        Member createdMember = memberCommandService.getMemberById(memberId);
        MemberResource memberResource = memberResourceAssembler.toResource(createdMember);

        return ResponseEntity.status(HttpStatus.CREATED).body(memberResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMember(@PathVariable Long id, @RequestBody UpdateMemberCommand command) {
        // Asegura que el ID sea correcto en el comando
        command = new UpdateMemberCommand(id, command.personName(), command.email(), command.streetAddress(), command.role());
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
        List<Member> members = memberCommandService.getAllMembers();
        List<MemberResource> memberResources = members.stream()
                .map(member -> memberResourceAssembler.toResource(member)) // Usar correctamente el ensamblador de instancia
                .collect(Collectors.toList());
        return ResponseEntity.ok(memberResources);
    }

}
