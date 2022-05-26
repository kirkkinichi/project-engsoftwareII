package com.engsoftwareII.converter;

import com.engsoftwareII.request.UserRequest;

import com.engsoftwareII.user.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UserRequestConverter {

    public Usuario toUser(UserRequest request){
        return new Usuario(request.getNome(), request.getIdade(), request.isMatricula());
    }

    public UserRequest toDTO(Usuario usuario){
        return new UserRequest(usuario.getId(),usuario.getNome(), usuario.getIdade(), usuario.isMatricula());
    }
}
