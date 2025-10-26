package br.com.alunoonline.api.service;
import br.com.alunoonline.api.model.Aluno;
import br.com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    public void criarAluno(Aluno aluno){
        alunoRepository.save(aluno);
    }

    public List<Aluno> buscarTodosAlunos(){
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarAlunoPorId(Long id){
        return alunoRepository.findById(id);
    }

    public void deletarAlunoPorId(Long id){
        alunoRepository.deleteById(id);
    }

    public void atualizarAlunoPorId(Long id, Aluno alunoAtualizado){
        //passo 1: ver se o aluno existe no DB
        Optional<Aluno> alunoDoBancoDeDados = buscarAlunoPorId(id);

        //passo 2: se não existir o aluno com o ID informado
        if(alunoDoBancoDeDados.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado no Banco de Dados");
        }
        else {
             //passo 3: primeiro armazena em uma variavel para depois edita-lo
             Aluno alunoParaEditar = alunoDoBancoDeDados.get();

             //passo 4: fazer set para atualizar o get vindo do front para o set ser enviado para o repository
             alunoParaEditar.setNomeCompleto(alunoAtualizado.getNomeCompleto());
             alunoParaEditar.setEmail(alunoAtualizado.getEmail());
             alunoParaEditar.setCpf(alunoAtualizado.getCpf());

             //passo 5: atualizou o objeto java, agora é enviar para o Banco de Dados para atualizar os dados
             alunoRepository.save(alunoParaEditar);
        }
    }




}
