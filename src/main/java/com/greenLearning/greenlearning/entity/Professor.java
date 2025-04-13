package com.greenLearning.greenlearning.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "tb_professor")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Professor  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotBlank(message = "Nome é um campo obrigatorio!")
    @Size(min = 3, max = 80, message = "Nome deve conter de 3 a 80 caracteres!")
    private String nome;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity usuario;
}
