package rest.rest;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

@RestController
@RequestMapping("/api/professores") 
public class ProfessorController {

    @Autowired
    private ProfessorRepo professorRepo;

    // Construtor vazio opcional
    public ProfessorController() {}

    @GetMapping
    public Iterable<Professor> getProfessores() {
        return professorRepo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Professor> getProfessor(@PathVariable long id) {
        return professorRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
    }

    @PostMapping
    public ResponseEntity<Professor> createProfessor(@RequestBody Professor p) {
        Professor savedProfessor = professorRepo.save(p);
        return new ResponseEntity<>(savedProfessor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@RequestBody Professor professor, @PathVariable long id) {
        return professorRepo.findById(id)
            .map(existingProfessor -> {
                professor.setId(existingProfessor.getId()); // Assegura que o ID esteja correto
                Professor updatedProfessor = professorRepo.save(professor);
                return ResponseEntity.ok(updatedProfessor);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfessor(@PathVariable long id) {
        if (professorRepo.existsById(id)) {
            professorRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Professor não encontrado");
        }
    }
}
