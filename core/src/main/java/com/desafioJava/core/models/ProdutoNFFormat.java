package com.desafioJava.core.models;

import com.google.gson.annotations.Expose;
import org.apache.sling.models.annotations.Model;
import org.osgi.resource.Resource;

@Model(adaptables = Resource.class)
public class ProdutoNFFormat {
    @Expose
    private int id;
    @Expose
    private String nome;
    @Expose
    private double preco;
    @Expose
    private  int qtn;

    public ProdutoNFFormat(int id, String nome, double preco, int qtn) {
        super();
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.qtn= qtn;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQtn() {
        return qtn;
    }

    public void setQtn(int qtn) {
        this.qtn = qtn;
    }
}
