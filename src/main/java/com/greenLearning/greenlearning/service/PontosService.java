package com.greenLearning.greenlearning.service;

import com.greenLearning.greenlearning.dto.PontosDTO;
import com.greenLearning.greenlearning.entity.Pontos;
import com.greenLearning.greenlearning.repository.PontosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PontosService {

    @Autowired
    PontosRepository repository;

    @Transactional
    public Pontos cadastrar(PontosDTO pontosDTO){
        Pontos pontos = new Pontos();

        BeanUtils.copyProperties(pontosDTO,pontos);

        return repository.save(pontos);
    }

    public Pontos buscarPorId(Long id) {
        Optional<Pontos> pontos = repository.findById(id);

        if (pontos.isEmpty()){
            Assert.isTrue(pontos.isEmpty(), "não foi possivel acessar a pontuação informada!");
            throw new NotFoundException("erro!!!");
        }
        return pontos.get();
    }

    public List<Pontos> listar() {
        List<Pontos> pontos = repository.findAll();

        Assert.isTrue(pontos != null,"não foi possivel localizar nenhum historio de pontuação cadastrado no banco!");

        return repository.findAll();
    }

    @Transactional
    public Pontos editar(Long id, PontosDTO pontosNovo){
        Pontos pontos = this.buscarPorId(id);

        Assert.isTrue(pontos !=null, "Não foi possivel localizar o historico de pontos informado!");
        pontos.setScore(pontosNovo.score());

        return repository.save(pontos);
    }
}
