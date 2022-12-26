package com.desafioJava.core.interfaces;

import com.desafioJava.core.models.NotaFiscal;

import java.util.List;

public interface NFiscaisDao {
    void salvarNF(NotaFiscal notaFiscal);
    void deletarNF(long numero);
    List<NotaFiscal> listaNF(long numero);
    boolean existe(long numero);
    public List<NotaFiscal> listaPorIdCliente(int idCliente);
}
