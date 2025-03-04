package com.greenLearning.greenlearning.controller;

import com.greenLearning.greenlearning.dto.AlunoDTO;
import com.greenLearning.greenlearning.entity.Aluno;
import com.greenLearning.greenlearning.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/aluno")
@CrossOrigin(origins = "*")
public class AlunoController {

    @Autowired
    AlunoService service;

    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@Valid @RequestBody final AlunoDTO alunoDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(alunoDTO));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<Aluno> buscarPorId(@RequestParam("id") final Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error buscar, " + e.getMessage());
        }
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<Aluno>> listar(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listar());

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Error listar, " + e.getMessage());
        }
    }

    @PutMapping(value = "/editar")
    public ResponseEntity<Aluno> editar(@RequestParam("id") final Long id, @Valid @RequestBody final AlunoDTO alunoDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.editar(id,alunoDTO));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error editar, " + e.getMessage());
        }
    }

    @DeleteMapping(value = "/deletar")
    public ResponseEntity<String> deletar(@RequestParam("id") final Long id){
        try {
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Aluno deletado com sucesso!");

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deletar, não foi possivel localizar o aluno informado");
        }
    }
}
