package com.desafioJava.core.service;

import com.desafioJava.core.interfaces.LogoutService;
import com.desafioJava.core.interfaces.LogoutDao;
import com.desafioJava.core.models.Logout;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component(immediate = true,service = LogoutService.class)
public class LogoutServiceImpl implements LogoutService {

    Gson gson = new Gson();

    @Reference
    private LogoutDao logoutDao;

    @Activate
    public LogoutServiceImpl(@Reference LogoutDao logoutDao){
        this.logoutDao=logoutDao;
    }


    @Override
    public void doGet(SlingHttpServletRequest req, SlingHttpServletResponse resp) {
        HttpSession sessao = req.getSession();
        try {

            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("aplication/json");

            //String jsonStr = IOUtils.toString(req.getReader());
            //Logout logout = gson.fromJson(jsonStr, Logout.class);
            //Logout usuario = logoutDao.logoutToken(logout.getToken());

            sessao.removeAttribute("usuario");

            if (sessao.getAttribute("usuario") != null) {
                resp.getWriter().write("Usuario ainda Logado!");
            }else{
                resp.getWriter().write("Usuario Deslogado com Sucesso");
            }

        } catch (Exception ex) {
            try {
                resp.getWriter().write("Necessario fazer Logout");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
