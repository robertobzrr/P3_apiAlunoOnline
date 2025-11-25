package br.com.alunoonline.api.service;

import br.com.alunoonline.api.dtos.AtualizarNotasRequestDTO;
import br.com.alunoonline.api.enums.MatriculaStatusEnum;
import br.com.alunoonline.api.model.MatriculaAluno;
import br.com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class MatriculaAlunoService {

    private static final Double MEDIA_PARA_APROVACAO = 7.0;

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


    public void atualizarNotas(Long id, AtualizarNotasRequestDTO atualizarNotasRequestDTO){
        //antes de atualizar, verificar se a matricula existe
        MatriculaAluno matriculaAluno = matriculaAlunoRepository.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException((HttpStatus.NOT_FOUND),
                    "Matricula não encontrada"));

        if(atualizarNotasRequestDTO.getNota1() != null){
            matriculaAluno.setNota1(atualizarNotasRequestDTO.getNota1());
        }

        if(atualizarNotasRequestDTO.getNota2() != null){
            matriculaAluno.setNota2(atualizarNotasRequestDTO.getNota2());
        }

        atualizarStatus(matriculaAluno);
        matriculaAlunoRepository.save(matriculaAluno);

        //mudar Status de acordo com a média
//        if(calcularMedia(atualizarNotasRequestDTO.getNota1(), atualizarNotasRequestDTO.getNota2()) >= 7){
//            matriculaAluno.setStatus(MatriculaStatusEnum.APROVADO);
//        }
//        else{
//            matriculaAluno.setStatus(MatriculaStatusEnum.REPROVADO);
//        }


    }


    private void atualizarStatus(MatriculaAluno matriculaAluno){
        Double nota1 = matriculaAluno.getNota1();
        Double nota2 = matriculaAluno.getNota2();

        if(nota1 != null && nota2 != null){
            Double media = calcularMedia(nota1, nota2);
            matriculaAluno.setStatus(media >= MEDIA_PARA_APROVACAO ? MatriculaStatusEnum.APROVADO : MatriculaStatusEnum.REPROVADO);
        }
    }




    private double calcularMedia(Double nota1, Double nota2){
        //operador ternário
        //se nota 1 e nota 2 nao for nulo faça depois do ? se não faça o depois do :
        // ? = if e : = else
        return (nota1 != null && nota2 != null) ? (nota1 + nota2)/2 : null;
    }





}
