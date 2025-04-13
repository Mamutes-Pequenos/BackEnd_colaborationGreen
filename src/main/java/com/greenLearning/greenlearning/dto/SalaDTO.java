package com.greenLearning.greenlearning.dto;

import com.greenLearning.greenlearning.entity.Aluno;
import com.greenLearning.greenlearning.entity.Professor;

import java.util.Set;
import java.util.UUID;

public record SalaDTO(Long id, String nome, Boolean ativo, Professor professor, Set<Aluno> alunos)  {}
