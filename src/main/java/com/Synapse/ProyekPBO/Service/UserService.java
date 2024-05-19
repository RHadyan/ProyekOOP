package com.Synapse.ProyekPBO.Service;

import com.Synapse.ProyekPBO.Models.RegistrationDto;
import com.Synapse.ProyekPBO.Models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);
}
