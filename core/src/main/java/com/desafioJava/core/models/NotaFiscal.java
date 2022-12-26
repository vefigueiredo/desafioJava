package com.desafioJava.core.models;

import com.google.gson.annotations.Expose;
import org.apache.sling.models.annotations.Model;
import org.osgi.resource.Resource;

import java.util.List;

@Model(adaptables = Resource.class)
public class NotaFiscal {
    @Expose
    private long numero;
    @Expose
    private int idProduto;
    @Expose
    private int idCliente;
    @Expose
    private double valor;
    @Expose
    private List<Produto> listaProdutos;

    public NotaFiscal(long numero, List<Produto> listaProdutos) {
        this.numero = numero;
        this.listaProdutos = listaProdutos;
    }
    public NotaFiscal(long numero,int idCliente, List<Produto> listaProdutos  ) {
        this.numero = numero;
        this.listaProdutos = listaProdutos;
        this.idCliente = idCliente;
    }
    public NotaFiscal(long numero, int idProduto, int idCliente, double valor) {
        this.numero = numero;
        this.idProduto = idProduto;
        this.idCliente = idCliente;
        this.valor = valor;
    }

    public long getNumero() {
        return numero;
    }


    public int getIdCliente() {
        return idCliente;
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public double getValor() {
        return valor;
    }
}
