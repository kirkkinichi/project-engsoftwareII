package com.engsoftwareII.repository.entity;

import com.engsoftwareII.user.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private int idade;
    private boolean matricula;

    public static UserEntity from(Usuario user) {
        return new UserEntity(user.getId(), user.getNome(), user.getIdade(), user.isMatricula());
    }

    public static Usuario from(UserEntity userEntity) {
        return new Usuario(userEntity.getId(), userEntity.getNome(), userEntity.getIdade(), userEntity.isMatricula());
    }

}
