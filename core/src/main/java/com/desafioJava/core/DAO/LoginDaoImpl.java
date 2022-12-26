package com.desafioJava.core.DAO;

import com.desafioJava.core.interfaces.LoginDao;
import com.desafioJava.core.models.Login;
import com.desafioJava.core.interfaces.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component(immediate = true,service = LoginDao.class)
public class LoginDaoImpl implements LoginDao{

    @Reference
    private DatabaseService databaseService;


    @Override
    public Login loginToken(Object username, String token) {
        return null;
    }

    @Override
    public Login getUsername(String username) {
        return null;
    }

    @Override
    public Login getToken(String token) {
        return null;
    }


    @Override
    public Login loginToken(String username, String token) {
        Login login = null;
        try (Connection connection = databaseService.getConnections()) {
            String sql = "SELECT USERNAME, TOKEN FROM usuario WHERE USERNAME = ? AND TOKEN = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,username);
            pstm.setString(2,token);
            pstm.execute();
            ResultSet result = pstm.getResultSet();
            while (result.next()) {
                login = new Login(result.getString("USERNAME"),result.getString("TOKEN"));
            }
            return login;

        } catch (Exception e) {
            new RuntimeException(e.getMessage() + " " + "Erro ao conectar no banco de dados");
        }
        return null;
    }
}
