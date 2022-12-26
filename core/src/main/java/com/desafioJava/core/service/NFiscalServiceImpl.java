package com.desafioJava.core.service;

import com.desafioJava.core.interfaces.FormatacaoNfiscal;
import com.desafioJava.core.interfaces.MsgService;
import com.desafioJava.core.interfaces.NFiscaisDao;
import com.desafioJava.core.interfaces.NotaFiscalService;
import com.desafioJava.core.models.Cliente;
import com.desafioJava.core.models.NotaFiscal;
import com.desafioJava.core.models.NotaFiscalFormat;
import com.desafioJava.core.models.Produto;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(immediate = true,service = NotaFiscalService.class)
public class NFiscalServiceImpl implements NotaFiscalService {

    private Gson gson = new Gson();
    @Reference
    private NFiscaisDao nFiscaisDao;

    @Reference
    private FormatacaoNfiscal formatacaoNfiscal;


    @Override
    public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("aplication/json");

        String numeroNF = request.getParameter("numero");

        HttpSession sessao = request.getSession();

        if(sessao.getAttribute("usuario") != null) {
            try {
                if (numeroNF != null && !numeroNF.equals("")) {

                    long numero = Long.parseLong(numeroNF);
                    List<NotaFiscalFormat> listFormatada;
                    listFormatada = formatacaoNfiscal.formatNF(numero);

                    if (listFormatada.get(0).getNumero() != 0) {
                        String json = gson.toJson(listFormatada);
                        response.getWriter().write(json);
                    } else {
                        throw new RuntimeException("Nota Fiscal não localizado");
                    }
                } else {
                    throw new RuntimeException("O numero da nota não pode ser vazio");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException ne) {
                try {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Não é um parâmetro válido");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                try {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write("Erro ao encontrar a Nota Fiscal");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else {
            response.getWriter().write("Necessario Efetuar o Login");
        }
    }


}