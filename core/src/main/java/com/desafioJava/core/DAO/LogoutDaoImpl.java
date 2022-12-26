package com.desafioJava.core.DAO;

import com.desafioJava.core.interfaces.LogoutDao;
import com.desafioJava.core.models.Logout;
import com.desafioJava.core.interfaces.DatabaseService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Component(immediate = true,service = LogoutDao.class)
public class LogoutDaoImpl implements LogoutDao{

    @Reference
    private DatabaseService databaseService;


    @Override
    public boolean admin(Logout logout) {
        return false;
    }

    @Override
    public Logout getToken(String token) {
        return null;
    }


    @Override
    public Logout logoutToken(String token) {
        Logout logout = null;
        String senha = null;
        try (Connection connection = databaseService.getConnections()) {
            String sql = "SELECT TOKEN FROM usuario WHERE TOKEN = ?";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1,token);
            pstm.execute();
            ResultSet result = pstm.getResultSet();
            while (result.next()) {
                logout = new Logout(result.getString("TOKEN"));
            }
            return logout;

        } catch (Exception e) {
            new RuntimeException(e.getMessage() + " " + "erro ao  no banco de dados");
        }
        return null;
    }
}
