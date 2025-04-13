package com.greenLearning.greenlearning.repository;

import com.greenLearning.greenlearning.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    boolean existsByNome(String nome);
}
