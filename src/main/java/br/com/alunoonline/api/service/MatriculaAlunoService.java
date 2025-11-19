package br.com.alunoonline.api.service;

import br.com.alunoonline.api.enums.MatriculaStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MatriculaAlunoService {

    @Autowired
    MatriculaAlunoRepository matriculaAlunoRepository;


    public void matricular(MatriculaAluno matriculaAluno){
        matriculaAluno.setStatus(MatriculaStatusEnum.MATRICULADO);
        matriculaAlunoRepository.save(matriculaAluno);
    }


    public void trancarMatricula(Long id){
        //verificar se existe a matricula
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Matricula nao existe"));

        //compara a matricula se ela ta matriculado se tiver troca para TRANCADO e salvar
        if(matriculaAluno.getStatus().equals(MatriculaStatusEnum.MATRICULADO)){
            matriculaAluno.setStatus(MatriculaStatusEnum.TRANCADO);

            matriculaAlunoRepository.save(matriculaAluno);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Só é possível trancar com o status matriculado");
        }

    }


    public void atualizarNotas(Long id){

    }


}
