package com.greenLearning.greenlearning.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "tb_sala")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sala  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotBlank(message = "Nome é um campo obrigatorio!")
    @Size(max = 25, message = "Nome deve conter até 25 caracteres!")
    private String nome;

    @Column
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "id_professor")
    private Professor professor;

    @ManyToMany
    @JoinTable(
            name = "tb_sala_alunos",
            joinColumns = @JoinColumn(name = "sala_id"),
            inverseJoinColumns = @JoinColumn(name = "aluno_id")
    )
    private Set<Aluno> alunos;

}
