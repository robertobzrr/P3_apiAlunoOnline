package br.com.alunoonline.api.service;


import br.com.alunoonline.api.model.Professor;
import br.com.alunoonline.api.repository.AlunoRepository;
import br.com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public void criarProfessor(Professor prof){
        professorRepository.save(prof);
    }

}
