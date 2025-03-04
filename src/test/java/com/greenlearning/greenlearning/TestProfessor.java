package com.greenlearning.greenlearning;

import com.greenLearning.greenlearning.controller.ProfessorController;
import com.greenLearning.greenlearning.dto.ProfessorDTO;
import com.greenLearning.greenlearning.entity.Professor;
import com.greenLearning.greenlearning.entity.UserEntity;
import com.greenLearning.greenlearning.entity.enuns.Roles;
import com.greenLearning.greenlearning.repository.ProfessorRepository;
import com.greenLearning.greenlearning.service.ProfessorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
class TestProfessor {


    @MockBean
    ProfessorRepository repository;

    @Autowired
    ProfessorController controller;

    @Autowired
    ProfessorService service;

    @BeforeEach
    void injectData(){

        UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);

        //BANCO DE DADOS
        Professor professor = new Professor(1L,"1 A", userEntity);

        //INSERÇÃO MANUAL PARA TESTAR CADASTRAR
        when(repository.save(Mockito.any(Professor.class))).thenAnswer(invocation -> {
            Professor professorSalvo = invocation.getArgument(0);
            professorSalvo.setId(1L);
            return professorSalvo;
        });

        List<Professor> professores = new ArrayList<>();
        professores.add(professor);

        //TESTAR BUSCAR POR ID
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(professor));

        //TESTAR LISTAR TODOS
        when(repository.findAll()).thenReturn(professores);

        //TESTAR ATUALIZAR
        Professor professorNovo = new Professor(1L,"1 A", userEntity);
        when(repository.save(professorNovo)).thenReturn(professores.get(0));
    }

    @Test
    @DisplayName("Cadastrou professor com sucesso!")
    void salvarTeste(){

        UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);

        var professor = controller.cadastrar(new ProfessorDTO(1L, userEntity,"Pedro Henrique"));

        Assertions.assertEquals(1L,professor.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED,professor.getStatusCode());
    }

    @Test
    @DisplayName("Buscou professor por id")
    void buscarPorIdTest(){

        var professor = controller.buscarPorId(1L);

        Assertions.assertEquals(1L, professor.getBody().getId());
        Assertions.assertEquals(HttpStatus.OK,professor.getStatusCode());
    }

    @Test
    @DisplayName("Listar todos os professores")
    void listarTodosTest(){

        var professor = controller.listar();
        List<Professor> professores = professor.getBody();

        Assertions.assertEquals(HttpStatus.OK, professor.getStatusCode());
        Assertions.assertEquals("1 A", professores.get(0).getNome());
        Assertions.assertEquals(1,professores.size());
    }

    @Test
    @DisplayName("Editou o professor com sucesso!")
    void atualizarTeste(){

        UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);
        ProfessorDTO professorDTO = new ProfessorDTO(1L, userEntity,"Jhonson e Jhonson");

        var professorNovo = controller.editar(professorDTO.id(),professorDTO);

        Assertions.assertEquals(HttpStatus.OK,professorNovo.getStatusCode());
        Assertions.assertEquals("Jhonson e Jhonson", professorNovo.getBody().getNome());

    }
}