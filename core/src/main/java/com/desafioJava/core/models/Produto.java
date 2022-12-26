package com.desafioJava.core.models;

import com.google.gson.annotations.Expose;
import org.apache.sling.models.annotations.Model;
import org.osgi.resource.Resource;

@Model(adaptables = Resource.class)
public class Produto {
    @Expose
    private int id;
    @Expose
    private String nome;
    @Expose
    private String categoria;
    @Expose
    private double preco;


    public Produto(int id, String nome, String categoria, double preco) {
        this.id = id;
        this.nome = nome;
        this.categoria = categoria;
        this.preco = preco;
    }

    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCategoria() {
        return categoria;
    }
    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "ID=" + id +
                ", NOME='" + nome + '\'' +
                ", CATEGORIA='" + categoria + '\'' +
                ", PRECO=" + preco +
                '}';
    }
}
