package com.greenLearning.greenlearning.service;

import com.greenLearning.greenlearning.dto.ProfessorDTO;
import com.greenLearning.greenlearning.entity.Professor;
import com.greenLearning.greenlearning.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository repository;

    @Transactional
    public Professor cadastrar(ProfessorDTO professorDTO) {
        Professor professor = new Professor();

        BeanUtils.copyProperties(professorDTO, professor);

        return repository.save(professor);
    }

    public Professor buscarPorId(Long id) {
        Optional<Professor> professor = repository.findById(id);

        if (professor.isEmpty()){
            Assert.isTrue(professor.isEmpty(), "Professor informado não foi localizado!");
            throw new NotFoundException("erro!!!");
        }
        return professor.get();
    }

        public Professor buscarProfessorPorIdUser(Long id) {
            Optional<Professor> professor = repository.buscarProfessorPorIdUser(id);

            if (professor.isEmpty()){
                Assert.isTrue(professor.isEmpty(), "Professor informado não foi localizado!");
                throw new NotFoundException("erro!!!");
            }
            return professor.get();
        }

    public List<Professor> listar() {
        List<Professor> professors = repository.findAll();

        Assert.isTrue(professors !=null, "não foi possivel localizar nenhum professor cadastrado!");

        return repository.findAll();
    }


    @Transactional
    public Professor editar(Long id, ProfessorDTO professorNovo) {
        Professor professor = this.buscarPorId(id);

        Assert.isTrue(professor !=null, "Não foi possivel localizar o professor informado!");
        professor.setNome(professorNovo.nome());

        return repository.save(professor);
    }
}
