package com.desafioJava.core.DAO;

import com.desafioJava.core.interfaces.ClienteDao;
import com.desafioJava.core.models.Cliente;
import com.desafioJava.core.interfaces.DatabaseService;
import com.desafioJava.core.models.Produto;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

@Component(immediate = true,service = ClienteDao.class)
public class ClienteDaoImpl implements ClienteDao {
    @Reference
    private DatabaseService databaseService;

    List<Cliente> lista = new ArrayList<>();

    @Override
    public List<Cliente> getClientes() {
        try (Connection connection = databaseService.getConnections()) {
            String sql = "SELECT ID,NOME FROM cliente";

            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.execute();
            ResultSet result = pstm.getResultSet();
            while (result.next()) {
                int id = result.getInt("ID");
                String nome = result.getString("NOME");

                lista.add(new Cliente(id, nome));
            }
            return lista;
        } catch (Exception e) {
            new RuntimeException(e.getMessage() + " " + "erro ao listar todos clientes do banco de dados");
        }
        return null;
    }

    @Override
    public Cliente getClientesById(int idGet) {
        Cliente cliente = null;
        try (Connection connection = databaseService.getConnections()) {
            String sql = "SELECT ID,NOME FROM cliente WHERE ID = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, idGet);
            pstm.execute();
            ResultSet result = pstm.getResultSet();
            while (result.next()) {
               // cliente = new Cliente(result.getInt("ID"), result.getString("NOME"));
                int id = result.getInt("ID");
                String nome=result.getString("NOME");

                cliente = new Cliente(id,nome);
            }
            return cliente;
        } catch (Exception e) {
            new RuntimeException(e.getMessage() + " " + "erro ao localizar cliente no banco de dados");
            return null;
        }

    }

    @Override
    public void setSalvar(Cliente cliente) {
        try (Connection connection = databaseService.getConnections()) {
            String sql = "INSERT INTO cliente (NOME) VALUES (?)";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cliente.getNome().toString());
            pstm.execute();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void deletar(int id) {
        try (Connection connection = databaseService.getConnections()) {
            String sql = "DELETE FROM cliente WHERE ID = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setInt(1, id);
            pstm.execute();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "Não foi possível deletar produto");
        }
    }
    @Override
    public void update(Cliente cliente) {
        try (Connection connection = databaseService.getConnections()) {
            String sql = "UPDATE cliente SET NOME = ? WHERE ID = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cliente.getNome());
            pstm.setInt(2, cliente.getId());
            pstm.execute();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage() + "Não foi possível atualizar produto");
        }
    }


    public boolean existe(Cliente cliente) {
        try (Connection connection = databaseService.getConnections()) {
            String sql = "SELECT ID,NOME FROM cliente WHERE NOME = ? ";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.setString(1, cliente.getNome());

            pstm.execute();
            ResultSet result = pstm.getResultSet();
            if (result.next()) {
                return true;
            }else
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean admin(Cliente cliente) {
        return false;
    }

    /*
    public boolean admin(Cliente cliente) {
        try (Connection connection = databaseService.getConnections()) {
            String sql = "SELECT ID,NOME,EMAIL FROM administradores WHERE EMAIL = ? AND SENHA = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);
            pstm.execute();
            ResultSet result = pstm.getResultSet();
            if (result.next()) {
                return true;
            }else
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    */
}