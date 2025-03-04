package com.greenlearning.greenlearning;

import com.greenLearning.greenlearning.controller.PontosController;
import com.greenLearning.greenlearning.dto.PontosDTO;
import com.greenLearning.greenlearning.entity.*;
import com.greenLearning.greenlearning.entity.enuns.Roles;
import com.greenLearning.greenlearning.repository.PontosRepository;
import com.greenLearning.greenlearning.service.PontosService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.mockito.Mockito.when;

@SpringBootTest
class TestPontos {

    @MockBean
    PontosRepository repository;

    @Autowired
    PontosController controller;

    @Autowired
    PontosService service;

    @BeforeEach
    void injectData(){

        UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);
        Professor professor = new Professor(1L,"1 A", userEntity);
        Aluno aluno = new Aluno(1L,"Pedro Henrique",2);
        Set<Aluno> alunos = new HashSet<>();
        alunos.add(aluno);
        Sala sala = new Sala(1L,"1 A",true,professor, alunos);

        //BANCO DE DADOS
        Pontos pontuacao = new Pontos(1L,aluno,1000);

        //INSERÇÃO MANUAL PARA TESTAR CADASTRAR
        when(repository.save(Mockito.any(Pontos.class))).thenAnswer(invocation -> {
            Pontos pontoSalvo = invocation.getArgument(0);
            pontoSalvo.setId(1L);
            return pontoSalvo;
        });

        List<Pontos> pontos = new ArrayList<>();
        pontos.add(pontuacao);

        //TESTAR BUSCAR POR ID
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(pontuacao));

        //TESTAR LISTAR TODOS
        when(repository.findAll()).thenReturn(pontos);

        //TESTAR ATUALIZAR
        Pontos pontuacaoNova = new Pontos(1L,aluno,1000);
        when(repository.save(pontuacaoNova)).thenReturn(pontos.get(0));
    }

    @Test
    @DisplayName("Cadastrou pontuacao com sucesso!")
    void salvarTeste(){

        UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);
        Professor professor = new Professor(1L,"1 A", userEntity);;
        Aluno aluno = new Aluno(1L,"Pedro Henrique",2);
        Set<Aluno> alunos = new HashSet<>();
        alunos.add(aluno);
        Sala sala = new Sala(1L,"1 A",true,professor, alunos);

        var pontos = controller.cadastrar(new PontosDTO(1L,aluno,1000));

        Assertions.assertEquals(1L,pontos.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED,pontos.getStatusCode());
    }

    @Test
    @DisplayName("Buscou pontuacao por id")
    void buscarPorIdTest(){

        var pontos = controller.buscarPorId(1L);

        Assertions.assertEquals(1L, pontos.getBody().getId());
        Assertions.assertEquals(HttpStatus.OK,pontos.getStatusCode());
    }

}