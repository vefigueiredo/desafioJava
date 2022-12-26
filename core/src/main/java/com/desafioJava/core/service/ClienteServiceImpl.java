package com.desafioJava.core.service;

import com.desafioJava.core.interfaces.ClienteDao;
import com.desafioJava.core.interfaces.ClienteService;
import com.desafioJava.core.interfaces.MsgService;
import com.desafioJava.core.models.Cliente;
import com.desafioJava.core.models.Produto;
import com.google.gson.Gson;
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


@Component(immediate = true,service = ClienteService.class)
public class ClienteServiceImpl implements ClienteService{
    Gson gson = new Gson();

    @Reference
    private ClienteDao clienteDao;


    @Override
    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("aplication/json");

        List<Cliente> listaCliente = new ArrayList<>();
        int cont=0;

        HttpSession sessao = req.getSession();

        if(sessao.getAttribute("usuario") != null) {
            try{
                TypeToken tt = new TypeToken<List<Cliente>>() {
                };
                String jsonStr = IOUtils.toString(req.getReader());
                listaCliente = gson.fromJson(jsonStr, tt.getType());


                for (Cliente cliente:listaCliente) {
                    boolean exis = clienteDao.existe(cliente);
                    if(exis==false) {
                        clienteDao.setSalvar(cliente);
                    }else{
                        resp.getWriter().write(cliente.getNome() + " ja existe");
                        cont++;
                    }
                }
                if (cont!= listaCliente.size()) {
                    resp.getWriter().write("Cliente(s) Cadastrado(s), com sucesso!");
                }
            }catch (IOException e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Dados obtidos não é um conteudo válido");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }catch (Exception e){
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write( "Dados incorreto, Erro ao salvar cliente no banco de dados");
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
                String idCliente = req.getParameter("id");
                String json = "";

                Cliente usuario1 = (Cliente) sessao.getAttribute("usuario1");

                if (idCliente != null) {
                    int id = Integer.parseInt(idCliente);
                    Cliente cliente = clienteDao.getClientesById(id);
                    if (cliente != null) {
                        json = gson.toJson(cliente);
                    } else {
                        resp.getWriter().write("Cliente não Encontrado");
                    }
                }
                else {
                    List<Cliente> list = clienteDao.getClientes();
                    json = gson.toJson(list);
                }
                resp.getWriter().write(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException nex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try {
                    resp.getWriter().write("Parâmetro incorreto");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try {
                    resp.getWriter().write("Cliente(s) não localizado(s)!");
                } catch (IOException ie) {
                    throw new RuntimeException(ie);
                }
            }
        }else {
            resp.getWriter().write("Necessario Efetuar o Login");
        }

    }

    @Override
    public void doPut(SlingHttpServletRequest req, SlingHttpServletResponse resp) throws IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("aplication/json");

        HttpSession sessao = req.getSession();

        if(sessao.getAttribute("usuario") != null) {
            try {

                int cont = 0;

                TypeToken tt = new TypeToken<List<Cliente>>() {
                };
                String jsonAtual = IOUtils.toString(req.getReader());
                List<Cliente> listCliente = gson.fromJson(jsonAtual, tt.getType());

                for (Cliente cliente : listCliente) {
                    cont++;
                    if (cliente.getId() != 0 & clienteDao.getClientesById (cliente.getId())!=null) {
                        clienteDao.update(cliente);
                    } else {
                        throw new NumberFormatException("cliente não localizado");
                    }
                }

                if (cont > 0) {
                    resp.getWriter().write("Cliente(s) Atualizado(s) com Sucesso!");
                } else {
                    resp.getWriter().write("Cliente(s) não Atualizado(s)!");
                }

            }catch (NumberFormatException nex){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try {
                    resp.getWriter().write("Parâmetro incorreto");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write("Erro ao atualizar");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
            String id=req.getParameter("id");
            try {
                if (id == null) {
                    TypeToken tt = new TypeToken<List<Cliente>>() {
                    };
                    String jsonAtual = IOUtils.toString(req.getReader());
                    List<Cliente> listCliente = gson.fromJson(jsonAtual, tt.getType());
                    for (Cliente clienteD : listCliente) {
                        if (clienteD.getId() != 0) {
                            clienteDao.deletar(clienteD.getId());
                        } else {
                            throw new NumberFormatException();
                        }
                    }
                    resp.getWriter().write("Clientes deletados com sucesso");
                } else{
                    int idDel = Integer.parseInt(id);
                    if(clienteDao.getClientesById(idDel)!=null) {
                        clienteDao.deletar(idDel);
                        resp.getWriter().write("Cliente deletado com sucesso");
                    }
                }
            }catch (NumberFormatException nex){
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                try {
                    resp.getWriter().write("Favor informar o ID do Cliente");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } catch (Exception e) {
                try {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.getWriter().write( "Cliente nao pode ser deletado");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else {
            resp.getWriter().write("Necessario Efetuar o Login");
        }

    }
}
