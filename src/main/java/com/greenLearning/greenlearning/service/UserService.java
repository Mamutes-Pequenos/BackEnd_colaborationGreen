package com.greenLearning.greenlearning.service;

import com.greenLearning.greenlearning.config.security.TokenService;
import com.greenLearning.greenlearning.dto.LoginDTO;
import com.greenLearning.greenlearning.dto.UserEntityDTO;
import com.greenLearning.greenlearning.entity.UserEntity;
import com.greenLearning.greenlearning.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Transactional
    public UserEntity cadastrar(UserEntityDTO userEntityDTO){
        UserEntity userEntity = new UserEntity();

        BeanUtils.copyProperties(userEntityDTO, userEntity);

        return repository.save(userEntity);
    }

    public UserEntity buscarPorId(Long id){
        Optional<UserEntity> user = repository.findById(id);

        if (user.isEmpty()){
            Assert.isTrue(user.isEmpty(), "não foi possivel localizar o user informado!");
            throw new NotFoundException("erro!!!");
        }
        return user.get();
    }

    public List<UserEntity> listar() {
        List<UserEntity> userEntities = repository.findAll();

        Assert.isTrue(userEntities !=null, "não foi possivel localizar nenhum usuario cadastrado!");

        return repository.findAll();
    }

    @Transactional
    public UserEntity editar(Long id, UserEntityDTO userNovo){
        UserEntity userEntity = this.buscarPorId(id);

        Assert.isTrue(userEntity !=null, "Não foi possivel localizar o usuario informado!");

        userEntity.setUsername(userNovo.username());

        return repository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = repository.findByUsername(username).orElseThrow();

        return new User(userDetails.getUsername(),userDetails.getPassword(),true,true,true,true,userDetails.getAuthorities());
    }

    public UserEntityDTO logar(LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.username(),
                        loginDTO.password()
                )
        );

        UserEntity user = repository.findByUsername(loginDTO.username()).orElseThrow();

        var jwtToken = tokenService.generateToken(user);

        UserEntityDTO userLogado = new UserEntityDTO(user.getId(),user.getUsername(),user.getRole(),jwtToken);

        return userLogado;
    }
}
