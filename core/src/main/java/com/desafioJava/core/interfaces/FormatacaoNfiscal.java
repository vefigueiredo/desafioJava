package com.desafioJava.core.interfaces;

import com.desafioJava.core.models.NotaFiscalFormat;

import java.util.List;

public interface FormatacaoNfiscal {
    List<NotaFiscalFormat> formatNF(long numero);
}
