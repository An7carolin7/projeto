package rest.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public class DisciplinaController {
    @RestController
@RequestMapping("/disciplinas")
    public class DisciplinaController {

    @Autowired
    private DisciplinaRepo disciplinaRepo;

    @GetMapping
    public List<Disciplina> getAllDisciplinas() {
        return disciplinaRepo.findAll();
    }

    @PostMapping
    public Disciplina createDisciplina(@RequestBody Disciplina disciplina) {
        return disciplinaRepo.save(disciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> updateDisciplina(@PathVariable Long id, @RequestBody Disciplina disciplinaDetails) {
        Disciplina disciplina = disciplinaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada para o ID: " + id));
        disciplina.setNome(disciplinaDetails.getNome());
        disciplina.setSigla(disciplinaDetails.getSigla());
        disciplina.setCurso(disciplinaDetails.getCurso());
        disciplina.setSemestre(disciplinaDetails.getSemestre());
        return ResponseEntity.ok(disciplinaRepo.save(disciplina));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteDisciplina(@PathVariable Long id) {
        Disciplina disciplina = disciplinaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disciplina não encontrada para o ID: " + id));
        disciplinaRepo.delete(disciplina);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

    
}
