package com.greenLearning.greenlearning.dto;

import com.greenLearning.greenlearning.entity.enuns.Roles;

public record UserEntityDTO(Long id, String username, Roles role,String token)  {}
