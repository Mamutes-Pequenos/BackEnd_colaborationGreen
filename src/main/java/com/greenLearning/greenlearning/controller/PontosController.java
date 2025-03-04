package com.greenLearning.greenlearning.controller;

import com.greenLearning.greenlearning.dto.PontosDTO;
import com.greenLearning.greenlearning.entity.Pontos;
import com.greenLearning.greenlearning.service.PontosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/pontos")
@CrossOrigin(origins = "*")
public class PontosController {

    @Autowired
    PontosService service;

    @PostMapping(value = "/finalizar")
    public ResponseEntity<Pontos> cadastrar(@Valid @RequestBody final PontosDTO pontosDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(pontosDTO));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error cadastrar, " + e.getMessage());
        }
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<Pontos> buscarPorId(@RequestParam("id") final Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error buscar, " + e.getMessage());
        }
    }

    @GetMapping(value = "/listar")
    public ResponseEntity<List<Pontos>> listar(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listar());

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Error listar, " + e.getMessage());
        }
    }

    @PutMapping(value = "/editar")
    public ResponseEntity<Pontos> editar(@RequestParam("id") final Long id, @Valid @RequestBody final PontosDTO pontosDTO){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.editar(id,pontosDTO));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error editar, " + e.getMessage());
        }
    }
}
