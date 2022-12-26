package com.desafioJava.core.servlets;

import com.desafioJava.core.interfaces.ClienteService;
import com.desafioJava.core.interfaces.ProdutoService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.apache.sling.api.servlets.ServletResolverConstants.*;

@Component(service = {Servlet.class}, property = {
        SLING_SERVLET_PATHS + "=" + "/bin/desafioJava/clientes",
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_GET,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_POST,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_DELETE,
        SLING_SERVLET_METHODS + "=" + HttpConstants.METHOD_PUT,
        SLING_SERVLET_EXTENSIONS + "=" + "json"
})

public class ClienteServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;

    @Reference
    private ClienteService clienteService;


    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        clienteService.doGet(req,resp);
        //resp.getWriter().write("do get cliente funcionando");
    }

    @Override
    protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        clienteService.doPost(req,resp);
        //resp.getWriter().write("do post cliente funcionando");
    }

    @Override
    protected void doPut(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        clienteService.doPut(req,resp);
        //resp.getWriter().write("do put cliente funcionando");
    }

    @Override
    protected void doDelete(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws ServletException, IOException {
        clienteService.doDelete(req,resp);
        //resp.getWriter().write("do delete cliente funcionando");
    }


}

