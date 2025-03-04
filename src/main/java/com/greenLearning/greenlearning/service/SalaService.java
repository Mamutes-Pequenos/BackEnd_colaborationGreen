package com.greenLearning.greenlearning.service;

import com.greenLearning.greenlearning.dto.SalaDTO;
import com.greenLearning.greenlearning.entity.Sala;
import com.greenLearning.greenlearning.repository.SalaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SalaService {

    @Autowired
    SalaRepository repository;

    @Transactional
    public Sala cadastrar(SalaDTO salaDTO){
        Sala sala = new Sala();

        BeanUtils.copyProperties(salaDTO,sala);

        return repository.save(sala);
    }

    public Sala buscarPorId(Long id) {
        Optional<Sala> sala = repository.findById(id);

        if (sala.isEmpty()) {
            Assert.isTrue(sala.isEmpty(), "não foi possivel localizar a sala informada!");
            throw new NotFoundException("erro!!!");
        }
        return sala.get();
    }

    public List<Sala> listar() {
        List<Sala> salas = repository.findAll();

        Assert.isTrue(salas !=null, "não foi possivel localizar nenhuma sala cadastrada!");

        return repository.findAll();
    }

    @Transactional
    public Sala editar(Long id, SalaDTO salaNovo){
        Sala sala = this.buscarPorId(id);

        Assert.isTrue(sala !=null, "Não foi possivel localizar a sala informada!");

        sala.setNome(salaNovo.nome());
        sala.setProfessor(salaNovo.professor());

        return repository.save(sala);
    }

    @Transactional
    public void delete(Long id){
        Sala sala = this.buscarPorId(id);

        repository.delete(sala);
    }

    public Boolean existsByNome(String nome){
        return repository.existsByNome(nome);
    }
}
