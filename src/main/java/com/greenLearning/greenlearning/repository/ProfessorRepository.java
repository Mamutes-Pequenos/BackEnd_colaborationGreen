package com.greenLearning.greenlearning.repository;

import com.greenLearning.greenlearning.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    @Query("SELECT professor FROM Professor professor WHERE professor.usuario.id = :id")
    Optional<Professor> buscarProfessorPorIdUser(@Param("id") final Long Id);

}
