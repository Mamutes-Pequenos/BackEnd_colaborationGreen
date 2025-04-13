package com.greenLearning.greenlearning.service;

import com.greenLearning.greenlearning.dto.AlunoDTO;
import com.greenLearning.greenlearning.entity.Aluno;
import com.greenLearning.greenlearning.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    @Autowired
    AlunoRepository repository;

    @Transactional
    public Aluno cadastrar(AlunoDTO alunoDTO){
        Aluno aluno = new Aluno();

        BeanUtils.copyProperties(alunoDTO,aluno);

        return repository.save(aluno);
    }

    public Aluno buscarPorId(Long id){
        Optional<Aluno> alunoOptional = repository.findById(id);

        if(alunoOptional.isEmpty()){
            Assert.isTrue(alunoOptional.isEmpty(), "Aluno informado nao foi localizado!");
            throw new NotFoundException("erro!!!");
        }
        return alunoOptional.get();
    }

    public List<Aluno> listar(){

        List<Aluno> alunos = repository.findAll();
        Assert.isTrue(alunos !=null, "não foi possivel localizar nenhum aluno cadastrado!");

        return repository.findAll();

    }

    @Transactional
    public Aluno editar(Long id, AlunoDTO alunoNovo){
        Aluno aluno = this.buscarPorId(id);

        Assert.isTrue(aluno !=null,"Não foi possivel localizar o aluno informado!");

        aluno.setNome(alunoNovo.nome());
        aluno.setIdade(alunoNovo.idade());

        return repository.save(aluno);
    }

    @Transactional
    public void delete(Long id){
        Aluno aluno = this.buscarPorId(id);

        repository.delete(aluno);
    }
}

