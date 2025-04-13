package com.greenLearning.greenlearning.repository;

import com.greenLearning.greenlearning.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
