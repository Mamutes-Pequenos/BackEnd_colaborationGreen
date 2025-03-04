package com.greenLearning.greenlearning.dto;

import com.greenLearning.greenlearning.entity.Aluno;

import java.util.UUID;

public record PontosDTO(Long id, Aluno aluno, Integer score) {}
