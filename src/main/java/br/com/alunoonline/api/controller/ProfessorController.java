package br.com.alunoonline.api.controller;


import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.service.AlunoService;
import br.com.alunoonline.api.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    AlunoService alunoService;
    @Autowired
    private ProfessorService professorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarProfessor(@RequestBody Professor prof){
        professorService.criarProfessor(prof);
    }


}
