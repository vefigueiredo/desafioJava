package com.desafioJava.core.service;

import com.desafioJava.core.interfaces.ProdutoDao;
import com.desafioJava.core.interfaces.ProdutoService;
import com.desafioJava.core.models.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


@Component(immediate = true,service = ProdutoService.class)
public class ProdutoServiceImpl implements ProdutoService {

    //private Gson gson = new Gson();
    @Reference
    private ProdutoDao produtoDao;

    @Activate
    public ProdutoServiceImpl(@Reference ProdutoDao produtoDao){
        this.produtoDao=produtoDao;
    }


    @Override
    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("aplication/json");

        Gson gson = new Gson();

        HttpSession sessao = req.getSession();

        if(sessao.getAttribute("usuario") != null) {
            try {
                int cont = 0;
                TypeToken tt = new TypeToken<List<Produto>>() {
                };
                String jsonStr = IOUtils.toString(req.getReader());
                List<Produto> listaproduto = gson.fromJson(jsonStr, tt.getType());
                for (Produto produto:listaproduto) {
                    if(produtoDao.existe(produto.getNome())==false){
                        cont++;
                        produtoDao.setSalvar(produto);
                    }
                    else {
                        resp.getWriter().write("Produto "+ produto.getNome() +" já Cadastrado(s)!");
                    }
                }
                if (cont > 0) {
                    resp.getWriter().write("Produto(s) Cadastrado(s) com Sucesso!");
                } else {
                    resp.getWriter().write("Produto(s) não Cadastrado(s)!");
                }

            } catch (IOException ex) {
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    String msnErr = " Conteudo vazio";
                    resp.getWriter().write(String.valueOf(msnErr));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }catch (Exception e){
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    String msnErr = " Conteudo nao e um json válido";
                    resp.getWriter().write(String.valueOf(msnErr));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else {
            resp.getWriter().write("Necessario Efetuar o Login");
        }
    }

    @Override
    public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("aplication/json");

        HttpSession sessao = req.getSession();

        if(sessao.getAttribute("usuario") != null) {
            Gson gson = new Gson();
            try {
                String idReq = req.getParameter("id");
                String palavraChave = req.getParameter("nome");
                String categor = req.getParameter("categoria");
                String orderByPreco = req.getParameter("order");

                if (idReq != null) {
                    int idGet = Integer.parseInt(idReq);

                    String json = gson.toJson(produtoDao.getFiltroId(idGet));
                    if (json.equals("null")) {
                        resp.getWriter().write("Produto não localizado 1");
                    } else {
                        resp.getWriter().write(json);
                    }
                } else if (palavraChave != null) {
                    String json = gson.toJson(produtoDao.getFiltroPalavraChave(palavraChave));
                    if (json.equals("null")) {
                        resp.getWriter().write("Produto não localizado 2");
                    } else {
                        resp.getWriter().write(json);
                    }
                } else if (orderByPreco != null) {
                    String json = null;
                    if (orderByPreco.equalsIgnoreCase("menor")) {
                        json = gson.toJson(produtoDao.getFiltroMenorPreco());
                    } else if (orderByPreco.equalsIgnoreCase("maior")) {
                        json = gson.toJson(produtoDao.getFiltroMaiorPreco());
                    }
                    if (json == (null)) {
                        resp.getWriter().write("Parâmetro de Ordenação incorreta");
                    } else {
                        resp.getWriter().write(json);
                    }
                } else if (categor != null) {
                    String json = gson.toJson(produtoDao.getFiltroCategoria(categor));
                    resp.getWriter().write(json);
                } else {
                    List<Produto> list = produtoDao.getProdutos();
                    String json = gson.toJson(list);
                    if (json.equalsIgnoreCase("[]")) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.getWriter().write("Não há produto no Banco de Dados");
                    } else {
                        resp.getWriter().write(json);
                    }
                }
            } catch (NumberFormatException nfe) {
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Erro ao listar Produtos(Parâmetro incorreto)");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Erro ao listar Produtos do Banco de Dados");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else {
            resp.getWriter().write("Necessario Efetuar o Login");
        }
    }

    @Override
    public void doPut(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Gson gson = new Gson();

        HttpSession sessao = req.getSession();

        if(sessao.getAttribute("usuario") != null) {
            int cont = 0;

            TypeToken tt = new TypeToken<List<Produto>>() {
            };
            String jsonAtual = IOUtils.toString(req.getReader());
            List<Produto> listProduto = gson.fromJson(jsonAtual, tt.getType());

            for (Produto produto : listProduto) {
                cont++;
                if (produtoDao.getFiltroId(produto.getId()) != null) {
                    produtoDao.update(produto);
                }
            }

            if (cont > 0) {
                resp.getWriter().write("Produto(s) Atualizado(s) com Sucesso!");
            } else {
                resp.getWriter().write("Produto(s) não Atualizado(s)!");
            }


        }else {
            resp.getWriter().write("Necessario Efetuar o Login");
        }

    }

    @Override
    public void doDelete(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("aplication/json");
        Gson gson = new Gson();

        HttpSession sessao = req.getSession();

        if(sessao.getAttribute("usuario") != null) {
            String id = req.getParameter("id");
            if (id !=null){
                int idDel = Integer.parseInt(id);
                if (produtoDao.getFiltroId(idDel)!=null) {
                    produtoDao.deletar(idDel);
                    resp.getWriter().write("Produto deletado com sucesso");
                }else{
                    resp.getWriter().write("Produto não encontrado");
                }
            }else{
                TypeToken tt = new TypeToken<List<Produto>>() {
                };
                String jsonAtual = IOUtils.toString(req.getReader());
                List<Produto> listProduto = gson.fromJson(jsonAtual, tt.getType());
                for (Produto produto:listProduto) {
                    if(produto.getId()!=0){
                        produtoDao.deletar(produto.getId());
                    }else{
                        throw new NumberFormatException();
                    }
                }
                resp.getWriter().write("Produtos deletados com sucesso");
            }
        }else {
            resp.getWriter().write("Necessario Efetuar o Login");
        }

    }
}
