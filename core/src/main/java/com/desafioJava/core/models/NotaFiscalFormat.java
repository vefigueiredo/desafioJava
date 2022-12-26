package com.desafioJava.core.models;

import com.google.gson.annotations.Expose;
import org.apache.sling.models.annotations.Model;
import org.osgi.resource.Resource;

import java.util.List;

@Model(adaptables = Resource.class)
public class NotaFiscalFormat {
    @Expose
    private long numero;
    @Expose
    private int idCliente;
    @Expose
    private double valor;


    private List<ProdutoNFFormat> listaProdutosFormat;

    public NotaFiscalFormat(long numero, int idCliente, double valor, List<ProdutoNFFormat> listaProdutosFormat) {
        this.numero = numero;
        this.idCliente = idCliente;
        this.valor = valor;
        this.listaProdutosFormat = listaProdutosFormat;
    }


    public long getNumero() {
        return numero;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public double getValor() {
        return valor;
    }

    public List<ProdutoNFFormat> getListaProdutosFormat() {
        return listaProdutosFormat;
    }
}
