package com.greenlearning.greenlearning;

import com.greenLearning.greenlearning.controller.AlunoController;
import com.greenLearning.greenlearning.dto.AlunoDTO;
import com.greenLearning.greenlearning.entity.Aluno;
import com.greenLearning.greenlearning.entity.Professor;
import com.greenLearning.greenlearning.entity.Sala;
import com.greenLearning.greenlearning.entity.UserEntity;
import com.greenLearning.greenlearning.entity.enuns.Roles;
import com.greenLearning.greenlearning.repository.AlunoRepository;
import com.greenLearning.greenlearning.service.AlunoService;
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

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class TestAluno {

	@MockBean
	AlunoRepository repository;

	@Autowired
	AlunoController controller;

	@Autowired
	AlunoService service;

	@BeforeEach
	void injectData() {

		UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);
		Professor professor = new Professor(1L,"1 A", userEntity);

		//BANCO DE DADOS
		Aluno aluno = new Aluno(1L, "Pedro Henrique", 2);

		//INSERÇÃO MANUAL PARA TESTAR CADASTRAR
		when(repository.save(Mockito.any(Aluno.class))).thenAnswer(invocation -> {
			Aluno alunoSalvo = invocation.getArgument(0);
			alunoSalvo.setId(1L);
			return alunoSalvo;
		});

		List<Aluno> alunos = new ArrayList<>();
		alunos.add(aluno);

		//TESTAR BUSCAR POR ID
		Long id = 1L;
		when(repository.findById(id)).thenReturn(Optional.of(aluno));

		//TESTAR LISTAR TODOS
		when(repository.findAll()).thenReturn(alunos);

		//TESTAR ATUALIZAR
		Aluno alunoNovo = new Aluno(1L, "Spining Splendi Spaining House", 2);
		when(repository.save(alunoNovo)).thenReturn(alunos.get(0));
	}

	@Test
	@DisplayName("Cadastrou aluno com sucesso!")
	void salvarTeste() {

		UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);
		Professor professor = new Professor(1L,"1 A", userEntity);

		var aluno = controller.cadastrar(new AlunoDTO(1L, "Pedro Henrique", 2));

		Assertions.assertEquals(1L, aluno.getBody().getId());
		Assertions.assertEquals(HttpStatus.CREATED, aluno.getStatusCode());
	}

	@Test
	@DisplayName("Buscou aluno por id")
	void buscarPorIdTest() {

		var endereco = controller.buscarPorId(1L);

		Assertions.assertEquals(1L, endereco.getBody().getId());
		Assertions.assertEquals(HttpStatus.OK, endereco.getStatusCode());
	}

	@Test
	@DisplayName("Listar todos os alunos")
	void listarTodosTest() {

		var aluno = controller.listar();
		List<Aluno> enderecos = aluno.getBody();

		Assertions.assertEquals(HttpStatus.OK, aluno.getStatusCode());
		Assertions.assertEquals("Pedro Henrique", enderecos.get(0).getNome());
		Assertions.assertEquals(1, enderecos.size());
	}

	@Test
	@DisplayName("Editou o aluno com sucesso!")
	void atualizarTeste() {

		UserEntity userEntity = new UserEntity(1L, "pedrohenrique2023@gmail.com", "123", Roles.PROFESSOR);
		Professor professor = new Professor(1L,"1 A", userEntity);

		AlunoDTO alunoDTO = new AlunoDTO(1L, "Spining Splendi Spaining House", 2);

		var aluno = controller.editar(alunoDTO.id(), alunoDTO);

		Assertions.assertEquals(HttpStatus.OK, aluno.getStatusCode());
		Assertions.assertEquals("Spining Splendi Spaining House", aluno.getBody().getNome());

	}

	@Test
	@DisplayName("Deletou o aluno com sucesso!")
	void deletarTest() {

		var aluno = controller.deletar(1L);

		Assertions.assertEquals(HttpStatus.OK, aluno.getStatusCode());
	}

	@Test
	void testeUnitarioUnitarios() {
		Sala salaMock = Mockito.mock(Sala.class);
		Aluno aluno = new Aluno(null, null, null);
		assertNull(aluno.getId());
		assertNull(aluno.getNome());
		assertNull(aluno.getIdade());
	}

}