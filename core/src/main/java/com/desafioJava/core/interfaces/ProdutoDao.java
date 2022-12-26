package com.desafioJava.core.interfaces;

import com.desafioJava.core.models.Produto;

import java.util.List;

public interface ProdutoDao {
    List<Produto> getProdutos();

    void setSalvar(Produto produtos);

    void deletar(int id);

    void update(Produto produto);

    Produto getFiltroId(int idGet);

    List<Produto> getFiltroMenorPreco();
    List<Produto> getFiltroMaiorPreco();

    List<Produto> getFiltroCategoria(String categoria);

    List<Produto> getFiltroPalavraChave(String palavra);
    boolean existe(String nome);


}
