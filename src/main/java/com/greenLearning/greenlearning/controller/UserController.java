package com.greenLearning.greenlearning.controller;

import com.greenLearning.greenlearning.dto.LoginDTO;
import com.greenLearning.greenlearning.dto.UserEntityDTO;
import com.greenLearning.greenlearning.entity.UserEntity;
import com.greenLearning.greenlearning.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    UserService service;

    @PostMapping(value = "/login")
    public ResponseEntity<UserEntityDTO> logar(@RequestBody LoginDTO loginDTO) {
        try {
            return ResponseEntity.ok(service.logar(loginDTO));

        }catch(AuthenticationException ex) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<UserEntity> cadastrar(@Valid @RequestBody final UserEntityDTO userEntityDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(userEntityDTO));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error cadastrar, " + e.getMessage());
        }
    }

    @GetMapping(value = "/buscar")
    public ResponseEntity<UserEntity> buscarPorId(@RequestParam("id") final Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error buscar, " + e.getMessage());
        }
    }

   


    @GetMapping(value = "/listar")
    public ResponseEntity<List<UserEntity>> listar(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.listar());

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Error listar, " + e.getMessage());
        }
    }

    @PutMapping(value = "/editar")
        public ResponseEntity<UserEntity> editar(@RequestParam("id") final Long id, @Valid @RequestBody final UserEntityDTO userNovo){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.editar(id,userNovo));

        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error editar, " + e.getMessage());
        }
    }


}
