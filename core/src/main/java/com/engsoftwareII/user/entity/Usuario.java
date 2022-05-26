package com.engsoftwareII.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario implements Serializable{

    private long id;
    private String nome;
    private int idade;
    private boolean matricula;

    public Usuario(String nome, int idade, boolean matricula) {
        this.nome = nome;
        this.idade = idade;
        this.matricula = matricula;
    }
}