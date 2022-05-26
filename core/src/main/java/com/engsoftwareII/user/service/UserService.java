package com.engsoftwareII.user.service;

import com.engsoftwareII.user.contract.UserRepository;
import com.engsoftwareII.user.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Usuario save(Usuario user) {
     return userRepository.save(user);
    }

    public Optional<Usuario> getById(Long id){
        return userRepository.findById(id);
    }


}