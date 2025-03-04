package com.greenLearning.greenlearning.repository;

import com.greenLearning.greenlearning.entity.Pontos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PontosRepository extends JpaRepository<Pontos, Long> {
}
