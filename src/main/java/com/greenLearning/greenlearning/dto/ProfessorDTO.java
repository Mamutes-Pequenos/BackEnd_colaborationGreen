package com.greenLearning.greenlearning.dto;

import com.greenLearning.greenlearning.entity.UserEntity;

import java.util.UUID;

public record ProfessorDTO(Long id, UserEntity usuario, String nome) {}
