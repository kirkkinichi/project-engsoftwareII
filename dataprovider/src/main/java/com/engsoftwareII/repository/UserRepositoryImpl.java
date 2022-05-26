package com.engsoftwareII.repository;

import com.engsoftwareII.repository.entity.UserEntity;
import com.engsoftwareII.user.contract.UserRepository;
import com.engsoftwareII.user.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private UserDao userDao;



    @Override
    public Usuario save(Usuario user) {
          UserEntity userEntity = userDao.save(UserEntity.from(user));
          return this.converter(userEntity);

    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return userDao.findById(id).map(UserEntity::from);
    }


    @Override
    public List<Usuario> encontrarTodos() {
        return userDao.findAll().stream().map(this::converter).collect(Collectors.toList());
    }

    private Usuario converter(UserEntity userEntity){
        Usuario usuario = new Usuario();
        usuario.setId(userEntity.getId());
        usuario.setNome(userEntity.getNome());
        usuario.setIdade(userEntity.getIdade());
        usuario.setMatricula(userEntity.isMatricula());

        return usuario;
    }
}
