package com.desafioJava.core.service;

import com.desafioJava.core.interfaces.LoginService;
import com.desafioJava.core.interfaces.LoginDao;
import com.desafioJava.core.models.Login;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component(immediate = true,service = LoginService.class)
public class LoginServiceImpl implements LoginService {

    Gson gson = new Gson();

    @Reference
    private LoginDao loginDao;

    @Activate
    public LoginServiceImpl(@Reference LoginDao loginDao){
        this.loginDao=loginDao;
    }


    @Override
    public void doPost(SlingHttpServletRequest req, SlingHttpServletResponse resp) {
        HttpSession sessao = req.getSession();
        try {

            String jsonStr = IOUtils.toString(req.getReader());
            Login login = gson.fromJson(jsonStr, Login.class);

            Login usuario = loginDao.loginToken(login.getUsername(),login.getToken());

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("aplication/json");

            if (usuario != null) {
                sessao.setAttribute("usuario", usuario);
                resp.getWriter().write("Usuario Logado com Sucesso!");
            }else{
                resp.getWriter().write("Usuario Nao Logado");
            }

        } catch (Exception ex) {
            try {
                resp.getWriter().write("Necessario fazer Login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
