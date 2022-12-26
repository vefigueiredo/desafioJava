package com.desafioJava.core.interfaces;

import com.desafioJava.core.models.Cliente;

import java.util.List;

public interface ClienteDao {
    List<Cliente> getClientes();
    Cliente getClientesById(int id);

    void setSalvar(Cliente cliente);

    void deletar(int id);

    void update(Cliente cliente);

    public boolean existe(Cliente cliente);

    public boolean admin(Cliente cliente);

}