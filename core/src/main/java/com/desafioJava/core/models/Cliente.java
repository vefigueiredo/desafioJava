package com.desafioJava.core.models;

import com.google.gson.annotations.Expose;
import org.apache.sling.models.annotations.Model;
import org.osgi.resource.Resource;

@Model(adaptables = Resource.class)
public class Cliente {
    @Expose
    private int id;
    @Expose
    private String nome;


    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Cliente(int id, String nome, String email, String senha) {
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

}
