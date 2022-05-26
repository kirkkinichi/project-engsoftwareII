package com.engsoftwareII.user.contract;

import com.engsoftwareII.user.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UserRepository  {

    Usuario save(Usuario user);

    Optional<Usuario> findById(Long id);

    List<Usuario> encontrarTodos();

}
