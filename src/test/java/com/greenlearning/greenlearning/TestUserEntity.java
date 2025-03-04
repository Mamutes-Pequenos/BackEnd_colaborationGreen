package com.greenlearning.greenlearning;

import com.greenLearning.greenlearning.controller.UserController;
import com.greenLearning.greenlearning.dto.UserEntityDTO;
import com.greenLearning.greenlearning.entity.UserEntity;
import com.greenLearning.greenlearning.entity.enuns.Roles;
import com.greenLearning.greenlearning.repository.UserRepository;
import com.greenLearning.greenlearning.service.UserService;
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
class TestUserEntity {

    @MockBean
    UserRepository repository;

    @Autowired
    UserController controller;

    @Autowired
    UserService service;

    @BeforeEach
    void injectData(){

        //BANCO DE DADOS
        UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);

        //INSERÇÃO MANUAL PARA TESTAR CADASTRAR
        when(repository.save(Mockito.any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity userEntitySalvo = invocation.getArgument(0);
            userEntitySalvo.setId(1L);
            return userEntitySalvo;
        });

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(userEntity);

        //TESTAR BUSCAR POR ID
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(userEntity));

        //TESTAR LISTAR TODOS
        when(repository.findAll()).thenReturn(userEntities);

        //TESTAR ATUALIZAR
        UserEntity userEntityNovo = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123456789", Roles.PROFESSOR);
        when(repository.save(userEntityNovo)).thenReturn(userEntities.get(0));
    }

    @Test
    @DisplayName("Cadastrou user com sucesso!")
    void salvarTeste(){

        var user = controller.cadastrar(new UserEntityDTO(1L, "pedrohenrique2023@gmail.com", Roles.PROFESSOR,""));

        Assertions.assertEquals(1L,user.getBody().getId());
        Assertions.assertEquals(HttpStatus.CREATED,user.getStatusCode());
    }

    @Test
    @DisplayName("Buscou user por id")
    void buscarPorIdTest(){

        var user = controller.buscarPorId(1L);

        Assertions.assertEquals(1L, user.getBody().getId());
        Assertions.assertEquals(HttpStatus.OK,user.getStatusCode());
    }

    @Test
    @DisplayName("Listar todas os users")
    void listarTodosTest(){

        var user = controller.listar();
        List<UserEntity> userEntities = user.getBody();

        Assertions.assertEquals(HttpStatus.OK, user.getStatusCode());
        Assertions.assertEquals("pedrohenrique2023@gmail.com", userEntities.get(0).getUsername());
        Assertions.assertEquals(1, userEntities.size());
    }

    @Test
    @DisplayName("Editou o user com sucesso!")
    void atualizarTeste(){

        UserEntityDTO userEntityDTO = new UserEntityDTO(1L, "pedro", Roles.PROFESSOR,"");

        var professorNovo = controller.editar(userEntityDTO.id(), userEntityDTO);

        Assertions.assertEquals(HttpStatus.OK,professorNovo.getStatusCode());
        Assertions.assertEquals("pedro", professorNovo.getBody().getUsername());

    }
}